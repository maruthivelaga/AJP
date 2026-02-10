# Project Structure Visualization

```
student-interest-app/
│
├── 📄 pom.xml                          # Maven configuration
├── 📄 README.md                        # Comprehensive documentation
├── 📄 QUICK_GUIDE.md                   # Quick reference guide
├── 📄 database-init.sql                # Database initialization script
├── 📄 .gitignore                       # Git ignore rules
│
└── 📁 src/
    └── 📁 main/
        ├── 📁 java/
        │   └── 📁 com/studentinterest/
        │       │
        │       ├── 📁 entity/                    # Entity Classes (Part A & B)
        │       │   ├── 📄 Student.java          # Student entity with Many-to-Many
        │       │   └── 📄 Interest.java         # Interest entity with Many-to-Many
        │       │
        │       ├── 📁 dao/                       # Data Access Objects
        │       │   ├── 📄 StudentDAO.java       # Student CRUD operations
        │       │   └── 📄 InterestDAO.java      # Interest CRUD + Report (Part B)
        │       │
        │       ├── 📁 servlet/                   # Controllers
        │       │   ├── 📄 StudentServlet.java   # Student request handler
        │       │   ├── 📄 InterestServlet.java  # Interest request handler
        │       │   └── 📄 ReportServlet.java    # Report handler (Part B)
        │       │
        │       └── 📁 util/                      # Utility Classes
        │           └── 📄 HibernateUtil.java    # SessionFactory manager
        │
        ├── 📁 resources/
        │   └── 📄 hibernate.cfg.xml             # Hibernate configuration
        │
        └── 📁 webapp/                            # Web Resources
            ├── 📁 WEB-INF/
            │   └── 📄 web.xml                   # Servlet configuration
            │
            ├── 📄 index.jsp                     # Home/Landing page
            │
            ├── 📄 students.jsp                  # List all students
            ├── 📄 student-form.jsp              # Add/Edit student (Part B: Multiple interests)
            │
            ├── 📄 interests.jsp                 # List all interests
            ├── 📄 interest-form.jsp             # Add/Edit interest
            │
            └── 📄 report.jsp                    # Student count report (Part B)
```

## 🎯 Component Relationships

```
┌─────────────────────────────────────────────────────────────────┐
│                         USER INTERFACE                          │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐       │
│  │ index.jsp│  │students  │  │interests │  │ report   │       │
│  │          │  │.jsp      │  │.jsp      │  │.jsp (B)  │       │
│  └────┬─────┘  └────┬─────┘  └────┬─────┘  └────┬─────┘       │
└───────┼─────────────┼─────────────┼─────────────┼──────────────┘
        │             │             │             │
        ▼             ▼             ▼             ▼
┌─────────────────────────────────────────────────────────────────┐
│                      SERVLET LAYER                              │
│       ┌──────────────┐  ┌──────────────┐  ┌──────────────┐    │
│       │   Student    │  │   Interest   │  │    Report    │    │
│       │   Servlet    │  │   Servlet    │  │   Servlet(B) │    │
│       └──────┬───────┘  └──────┬───────┘  └──────┬───────┘    │
└──────────────┼──────────────────┼──────────────────┼────────────┘
               │                  │                  │
               ▼                  ▼                  ▼
┌─────────────────────────────────────────────────────────────────┐
│                        DAO LAYER                                │
│          ┌──────────────┐         ┌──────────────┐             │
│          │  StudentDAO  │         │ InterestDAO  │             │
│          │  - getAll()  │         │ - getAll()   │             │
│          │  - getById() │         │ - getById()  │             │
│          │  - save()    │         │ - save()     │             │
│          │  - delete()  │         │ - delete()   │             │
│          └──────┬───────┘         │ - getReport()│ (Part B)    │
│                 │                 └──────┬───────┘             │
└─────────────────┼────────────────────────┼─────────────────────┘
                  │                        │
                  └────────┬───────────────┘
                           ▼
┌─────────────────────────────────────────────────────────────────┐
│                    HIBERNATE UTIL                               │
│                  ┌──────────────┐                               │
│                  │ HibernateUtil│                               │
│                  │ SessionFactory                               │
│                  └──────┬───────┘                               │
└─────────────────────────┼───────────────────────────────────────┘
                          ▼
┌─────────────────────────────────────────────────────────────────┐
│                      ENTITY LAYER                               │
│       ┌──────────────┐         ┌──────────────┐                │
│       │   Student    │◄───────►│   Interest   │                │
│       │  - id        │  Many   │  - id        │                │
│       │  - name      │   to    │  - title     │                │
│       │  - age       │  Many   │  - desc      │                │
│       │  - email     │ (Part B)│  - category  │                │
│       │  - interests │         │  - students  │                │
│       └──────────────┘         └──────────────┘                │
└─────────────────────────────────────────────────────────────────┘
                          ▼
┌─────────────────────────────────────────────────────────────────┐
│                     DATABASE (MySQL)                            │
│  ┌──────────┐  ┌─────────────────┐  ┌──────────┐              │
│  │ students │  │student_interests│  │interests │              │
│  │          │  │  (Join Table)   │  │          │              │
│  └────┬─────┘  └─────┬───────────┘  └────┬─────┘              │
│       │              │                    │                     │
│       └──────────────┴────────────────────┘                     │
└─────────────────────────────────────────────────────────────────┘
```

## 📋 Key Features Flow

### Part A: Basic CRUD
```
User → Select "Manage Students"
     → StudentServlet (action=list)
     → StudentDAO.getAll()
     → Hibernate Query
     → MySQL Database
     → Return List<Student>
     → Display in students.jsp
```

### Part B: Multiple Interests
```
User → Add/Edit Student
     → Select Multiple Interests (Checkboxes)
     → StudentServlet (action=save)
     → Process Selected Interests
     → Set<Interest> in Student
     → StudentDAO.saveOrUpdate()
     → Hibernate saves Many-to-Many relationship
     → Updates student_interests join table
     → MySQL Database
```

### Part B: Report Generation
```
User → Click "View Reports"
     → ReportServlet
     → InterestDAO.getStudentCountByCategory()
     → HQL Query with GROUP BY
     → MySQL Database
     → Return Map<Category, Count>
     → Display in report.jsp with statistics
```

## 🔄 Data Flow Example

### Adding a Student with Multiple Interests:

1. **User Action**: Fills form with name="John", age=20, email="john@email.com"
2. **Selects**: "Programming" and "Sports" interests (Part B)
3. **Submit**: POST to /student?action=save
4. **Servlet**: Creates Student object, attaches selected interests
5. **DAO**: Calls session.saveOrUpdate(student)
6. **Hibernate**: 
   - Inserts into `students` table
   - Inserts relationships into `student_interests` join table
7. **Database**: Data persisted
8. **Response**: Redirect to students list

## 🗄️ Database Schema

```
┌─────────────────┐
│    students     │
├─────────────────┤
│ student_id (PK) │◄─────┐
│ name            │      │
│ age             │      │
│ email (UNIQUE)  │      │
└─────────────────┘      │
                         │
                    ┌────┴──────────────┐
                    │ student_interests │ (Join Table)
                    ├───────────────────┤
                    │ student_id (FK)   │
                    │ interest_id (FK)  │
                    └────┬──────────────┘
                         │
┌─────────────────┐      │
│   interests     │      │
├─────────────────┤      │
│ interest_id(PK) │◄─────┘
│ title           │
│ description     │
│ category        │
└─────────────────┘
```

## 🎯 Assignment Coverage

| Requirement | Implementation | File Location |
|-------------|----------------|---------------|
| Student Entity | Hibernate Entity with @ManyToMany | Student.java |
| Interest Entity | Hibernate Entity with @ManyToMany | Interest.java |
| CRUD Students | Full CRUD operations | StudentDAO.java, StudentServlet.java |
| CRUD Interests | Full CRUD operations | InterestDAO.java, InterestServlet.java |
| Multiple Interests | Many-to-Many with Set<Interest> | Student.java + student-form.jsp |
| Report Feature | HQL GROUP BY query | InterestDAO.java, ReportServlet.java |
| Web Interface | JSP pages with forms | *.jsp files |
| Hibernate Config | Database + Entity mapping | hibernate.cfg.xml |
