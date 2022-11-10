# QAM2 Appointment Management System

### This application manages appointments and customers by interacting with a MySQL database.

#### Jeramiah Coffey |   (512) 968-8615 | jeramiahcoffey.dev@gmail.com

This project was built with IntelliJ IDEA 2022.2.3 (Community Edition). Development began on October 25, 2022.

Oracle OpenJDK Version 17.0.5

JavaFX SDK Version 17.0.2

MySQL Connector J Version 8.0.31

### Additional Report Functionality:

The Application implements reporting functionality that goes beyond the requirements set forth.

Appointment totals reports were required, and included the ability to filter by Type and Month. The solution provided
implements this reporting functionality, but with a deeper level of optional detail. Users can choose to select between
one, some, or all of the filters, which include Month, Type, and Customer ID.

### Directions

- Launch the application. Login with a valid username and password. On successful login, you will be taken to the
  Appointments page.
- From the Appointments page, you can manage existing appointments and add new appointments. Data is entered on a
  separate window into form fields.
- Appointments may also be deleted from the Appointments page.
- Appointments can be filtered by All, This Month, or This Week.
- Clicking on Customers takes you to the Customers page.
- From the Customers page, you can manage existing customers and add new customers. Data is entered on a separate window
  into form fields.
- Customers may also be deleted from the Customers page.
- Clicking on Reports takes you to the Reports page. On this page there are two tabs for the two different types of
  reports.
- On the Appointment Totals tab, users can view the total appointment records by utilizing 0 - 3 of the available
  filters.
- On the Contact Schedule tab, users can view all of a Contact's appointments sorted by start date/time in a Table View.
- Clicking close returns the user to the Appointments view.
- Clicking logout closes the application.