package controller;

import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.Session;
import com.mysql.cj.protocol.Message;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import controller.manage.BookController;
import controller.manage.ReturnBookController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.AvailableBook;
import model.Book;
import model.IssueBook;
import model.ReturnBook;
import sun.plugin2.message.transport.Transport;
import view.tm.ReturnBookTm;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.mysql.cj.protocol.Message.*;
import static javax.mail.Transport.send;
import static sun.plugin2.message.transport.Transport.*;

public class ReturnBookFormController {
    public Label lblDate;
    public Label lblTime;

    public JFXTextField txtIssueId;
    public JFXTextField txtIssueDate;
    public JFXTextField txtEndDate;
    public JFXTextField txtBookId;
    public JFXTextField txtEmpId;
    public JFXTextField txtMemberId;
    public JFXTextField txtBookName;
    public JFXTextField txtWritterName;
    public JFXTextField txtLan;
    public JFXTextField txtPrice;
    public JFXTextField txtBookType;
    public JFXTextField txtAddDAte;
    public Label lblDateDates;
    public Label lblFines;
    public JFXTextField txtSearch;
    public TableView <ReturnBookTm>tblreturnBook;
    public TableColumn colIssueBookId;
    public TableColumn colMemberId;
    public TableColumn colIssueDate;
    public TableColumn colEndDate;
    public TableColumn colBookId;
    public TableColumn colEmpId;
    public TableColumn colLateDates;
    public TableColumn colTotalFines;
    public TableColumn colSendMail;
    public Label lblId;


    ObservableList<ReturnBookTm> tableData = FXCollections.observableArrayList();

    public void initialize(){

        loadDateAndTime();
        lastRBookId();
        loadTableData();

        colIssueBookId.setCellValueFactory(new PropertyValueFactory<>("issueId"));
        colIssueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colLateDates.setCellValueFactory(new PropertyValueFactory<>("lateDate"));
        colTotalFines.setCellValueFactory(new PropertyValueFactory<>("fines"));
        colSendMail.setCellValueFactory(new PropertyValueFactory<>("button"));

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });
    }

    private void loadDateAndTime() {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }


    public void lastRBookId() {
        try {
            String rbookId = ReturnBookController.getLastRId();
            String finalId = "R-001";

            if (rbookId != null) {

                String[] splitString = rbookId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "R-00" + id;
                } else if (id < 100) {
                    finalId = "R-0" + id;
                } else {
                    finalId = "R-" + id;
                }
                lblId.setText(finalId);
            } else {
                lblId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void AddReturnBook(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       ReturnBook b=new ReturnBook(
               lblId.getText(),
                txtIssueId.getText(),txtIssueDate.getText(),txtEndDate.getText(),lblDate.getText(),txtBookId.getText(),txtMemberId.getText(),
                txtEmpId.getText(),Integer.parseInt(lblDateDates.getText()),Double.parseDouble(lblFines
               .getText())
        );

        AvailableBook a=new AvailableBook(
                txtBookId.getText(),txtBookName.getText(),txtWritterName.getText(),txtLan.getText(),Double.parseDouble(txtPrice.getText()),txtBookType.getText()
                ,txtAddDAte.getText()
        );

        boolean isDeleted=new ReturnBookController().deleteIssueBook(txtBookId.getText());
        if(new ReturnBookController().saveReturnBook(b,a)  && isDeleted){
            new Alert(Alert.AlertType.INFORMATION,"Success").show();
            lastRBookId();
            loadTableData();
           txtIssueId.clear();
            txtIssueDate.clear();
            txtEndDate.clear();
            txtBookId.clear();
            txtMemberId.clear();
            txtEmpId.clear();
            txtBookName.clear();
            txtWritterName.clear();
            txtLan.clear();
            txtPrice.clear();
            txtBookType.clear();
            txtAddDAte.clear();
            lblDateDates.setText("0");
            lblFines.setText("0");
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }

    }

    public void SetAllDataOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String Id = txtBookId.getText();
        IssueBook b1= new ReturnBookController().getIssueBooks(Id);
        Book b=new BookController().getBooks(Id);
        if (b1==null ) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(b1,b);
            if(! txtEndDate.getText().equalsIgnoreCase("") && !lblDate.getText().equalsIgnoreCase("")) {
                CalculateLateDates();
            }
            CalculateFines();
        }
    }

    void setData(IssueBook b,Book b1){
        txtIssueId.setText(b.getIssueId());
        txtIssueDate.setText(b.getIssueDate());
        txtEndDate.setText(b.getEndDate());
        txtBookId.setText(b.getBookId());
        txtMemberId.setText(b.getMemberId());
        txtEmpId.setText(b.getEmpId());
        txtBookName.setText(b1.getBookName());
        txtWritterName.setText(b1.getWritterName());
        txtLan.setText(b1.getBookLanguage());
        txtPrice.setText(String.valueOf(b1.getPrice()));
        txtBookType.setText(b1.getType());
        txtAddDAte.setText(b1.getAddDate());

    }

    public void CalculateLateDates() {
        /*String dateStart = "11/03/14 09:29:58";
        String dateStop = "11/03/14 09:33:43";

        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Get msec from each, and subtract.
        long diff = d2.getTime() - d1.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        System.out.println(diff);
        System.out.println("Time in seconds: " + diffSeconds + " seconds.");
        System.out.println("Time in minutes: " + diffMinutes + " minutes.");
        System.out.println("Time in hours: " + diffHours + " hours.");

*/
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String inputString1 = txtEndDate.getText();
        String inputString2 = lblDate.getText();
        System.out.println(inputString1);
        System.out.println(inputString2);
        /*String inputString1 = "2021-09-07";
        String inputString2 = "2021-09-17";*/
        if ((!inputString1.equals("") && !inputString1.equals(""))) {
            try {
                Date date1 = myFormat.parse(inputString1);
                Date date2 = myFormat.parse(inputString2);
                long diff = date2.getTime() - date1.getTime();
                System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                lblDateDates.setText(String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void CalculateFines(){
        Double DayFineFee=5.00;
        int dayCount=Integer.parseInt(lblDateDates.getText());

        Double totalFines=(DayFineFee*dayCount);
        lblFines.setText(String.valueOf(totalFines));
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        search(txtSearch.getText());
    }

    private void search(String value){
        try {

            List<ReturnBook> allBooks = ReturnBookController.searchReturnBook(value);
            ObservableList<ReturnBookTm> tableData = FXCollections.observableArrayList();
            for (ReturnBook b : allBooks) {
                Button send = new Button("send Mail");
                send.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                       /* //System.out.println("working");
                        CustomerTM item = tblCustomer.getSelectionModel().getSelectedItem();
                        showUpdateForm(item);*/
                    }
                });
                tableData.add(new ReturnBookTm(
                        b.getReturnId(),
                      b.getIssueId(),
                        b.getIssueDate(),
                        b.getEndDate(),
                        b.getReturnDate(),
                        b.getBookId(),
                        b.getMemberId(),
                        b.getEmpId(),
                        b.getLateDate(),
                        b.getFines(),
                        send
                ));
            }
            tblreturnBook.setItems(tableData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public  void sendMail() throws NamingException {
/*// Recipient's email ID needs to be mentioned.
        String to = "abcd@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "web@gmail.com";

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try{
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("This is actual message");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }*/
    }



    public void loadTableData() {
        try {

            List<ReturnBook> allReturns =ReturnBookController.getAllMember();
            ArrayList<ReturnBookTm> returnBook=new ArrayList<>();
            for (ReturnBook b : allReturns) {
                Button send = new Button("send Mail");
                returnBook.add(new ReturnBookTm(
                        b.getReturnId(),
                        b.getIssueId(),
                        b.getIssueDate(),
                        b.getEndDate(),
                        b.getReturnDate(),
                        b.getBookId(),
                        b.getMemberId(),
                        b.getEmpId(),
                        b.getLateDate(),
                        b.getFines(),
                        send

                ));
            }
            tableData.clear();
            tableData.addAll(returnBook);
            tblreturnBook.setItems(tableData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
