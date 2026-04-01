package com.campus.lostfound.service;

import com.campus.lostfound.model.DisputeCase;
import com.campus.lostfound.model.ItemReport;
import com.campus.lostfound.model.ItemStatus;
import com.campus.lostfound.model.ItemType;
import com.campus.lostfound.model.MatchNotification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class LostFoundService {
    private static final LostFoundService INSTANCE = new LostFoundService();

    private final List<ItemReport> reports = new ArrayList<>();
    private final List<MatchNotification> notifications = new ArrayList<>();
    private final List<DisputeCase> disputes = new ArrayList<>();

    private final AtomicInteger itemIdCounter = new AtomicInteger(1000);
    private final AtomicInteger notificationIdCounter = new AtomicInteger(1);
    private final AtomicInteger disputeIdCounter = new AtomicInteger(1);

    private LostFoundService() {
    }

    public static LostFoundService getInstance() {
        return INSTANCE;
    }

    public synchronized ItemReport reportItem(ItemType type, String studentName, String email,
                                              String description, String location) {
        ItemReport report = new ItemReport(
                itemIdCounter.incrementAndGet(),
                type,
                studentName,
                email,
                description,
                location,
                LocalDateTime.now(),
                type == ItemType.LOST,
                ItemStatus.OPEN
        );
        reports.add(report);
        return report;
    }

    public synchronized List<ItemReport> getAllReports() {
        return new ArrayList<>(reports);
    }

    public synchronized List<ItemReport> searchByDescription(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(reports);
        }
        String normalized = query.toLowerCase(Locale.ROOT).trim();
        return reports.stream()
                .filter(item -> item.getDescription() != null
                        && item.getDescription().toLowerCase(Locale.ROOT).contains(normalized))
                .collect(Collectors.toList());
    }

    public synchronized List<ItemReport> getPendingFoundVerification() {
        return reports.stream()
                .filter(item -> item.getType() == ItemType.FOUND && !item.isVerified())
                .collect(Collectors.toList());
    }

    public synchronized boolean verifyFoundItem(int itemId) {
        for (ItemReport report : reports) {
            if (report.getId() == itemId && report.getType() == ItemType.FOUND) {
                report.setVerified(true);
                return true;
            }
        }
        return false;
    }

    public synchronized int runMatching() {
        int created = 0;
        List<ItemReport> openLost = reports.stream()
                .filter(item -> item.getType() == ItemType.LOST && item.getStatus() == ItemStatus.OPEN)
                .collect(Collectors.toList());

        List<ItemReport> verifiedFound = reports.stream()
                .filter(item -> item.getType() == ItemType.FOUND
                        && item.isVerified()
                        && item.getStatus() == ItemStatus.OPEN)
                .collect(Collectors.toList());

        for (ItemReport lost : openLost) {
            for (ItemReport found : verifiedFound) {
                if (isAlreadyNotified(lost.getId(), found.getId())) {
                    continue;
                }

                double score = calculateSimilarity(lost, found);
                if (score >= 0.45) {
                    notifications.add(new MatchNotification(
                            notificationIdCounter.getAndIncrement(),
                            lost.getId(),
                            found.getId(),
                            lost.getEmail(),
                            "Potential match identified for your lost item report.",
                            "Please meet campus help desk at 4:00 PM with student ID and proof of ownership.",
                            LocalDateTime.now()
                    ));
                    lost.setStatus(ItemStatus.MATCHED);
                    found.setStatus(ItemStatus.MATCHED);
                    created++;
                    break;
                }
            }
        }
        return created;
    }

    public synchronized List<MatchNotification> getNotifications() {
        return new ArrayList<>(notifications);
    }

    public synchronized boolean openDispute(int itemId, String reason) {
        ItemReport target = findItem(itemId);
        if (target == null) {
            return false;
        }
        target.setStatus(ItemStatus.DISPUTED);
        disputes.add(new DisputeCase(
                disputeIdCounter.getAndIncrement(),
                itemId,
                reason,
                "OPEN",
                null,
                LocalDateTime.now(),
                null
        ));
        return true;
    }

    public synchronized boolean resolveDispute(int disputeId, String resolution) {
        for (DisputeCase dispute : disputes) {
            if (dispute.getId() == disputeId && "OPEN".equalsIgnoreCase(dispute.getStatus())) {
                dispute.setStatus("RESOLVED");
                dispute.setResolution(resolution);
                dispute.setResolvedAt(LocalDateTime.now());

                ItemReport item = findItem(dispute.getItemId());
                if (item != null) {
                    item.setStatus(ItemStatus.RETRIEVED);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized List<DisputeCase> getDisputes() {
        return new ArrayList<>(disputes);
    }

    private ItemReport findItem(int itemId) {
        return reports.stream()
                .filter(item -> item.getId() == itemId)
                .findFirst()
                .orElse(null);
    }

    private boolean isAlreadyNotified(int lostId, int foundId) {
        return notifications.stream()
                .anyMatch(notification -> notification.getLostItemId() == lostId
                        && notification.getFoundItemId() == foundId);
    }

    private double calculateSimilarity(ItemReport lost, ItemReport found) {
        Set<String> lostTokens = tokenize(lost.getDescription());
        Set<String> foundTokens = tokenize(found.getDescription());
        if (lostTokens.isEmpty() || foundTokens.isEmpty()) {
            return 0.0;
        }

        Set<String> intersection = new HashSet<>(lostTokens);
        intersection.retainAll(foundTokens);

        Set<String> union = new HashSet<>(lostTokens);
        union.addAll(foundTokens);

        double jaccard = (double) intersection.size() / union.size();
        boolean locationNear = lost.getLocation() != null
                && found.getLocation() != null
                && found.getLocation().toLowerCase(Locale.ROOT)
                .contains(lost.getLocation().toLowerCase(Locale.ROOT));

        return locationNear ? Math.min(1.0, jaccard + 0.2) : jaccard;
    }

    private Set<String> tokenize(String description) {
        Set<String> tokens = new HashSet<>();
        if (description == null) {
            return tokens;
        }

        String[] words = description.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9 ]", " ")
                .split("\\s+");

        for (String word : words) {
            if (word.length() > 2) {
                tokens.add(word);
            }
        }
        return tokens;
    }
}
