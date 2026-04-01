# Lost and Found Management System (Struts2)

A university campus web application to report lost/found items, search matching items, notify affected students, and manage admin verification/dispute workflows.

## Features

- Report lost or found items with description and location.
- Search items by description.
- Auto-match lost reports with verified found reports.
- Generate notifications with a suggested pickup meeting.
- Admin dashboard for:
  - verifying found items,
  - opening disputes,
  - resolving disputes.

## Tech Stack

- Java 11
- Apache Struts2
- JSP + Struts tags
- Maven (WAR packaging)

## Run

```bash
mvn clean package
```

Deploy the generated WAR from `target/lost-and-found-1.0.0.war` to a Servlet container (Tomcat 9+ recommended).

## Main URLs

- `/home` - Dashboard
- `/reportForm` - Report item
- `/searchItems` - Search items
- `/notifications` - Matching and notifications
- `/adminDashboard` - Admin features
