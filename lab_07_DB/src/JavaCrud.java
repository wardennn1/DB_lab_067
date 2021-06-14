import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class JavaCrud {
    private JPanel Main;
    private JTextField cb_name;
    private JTextField cb_label;
    private JComboBox cb_format;
    private JFormattedTextField cb_pub_date;
    private JFormattedTextField cb_FOC;
    private JTextField cb_label_id;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField cb_label_description;
    private JButton lastMonthReleasesButton;
    private JButton formatStatsInPeriodButton;
    private JTextField cb_description;
    private JLabel desc;
    private JCheckBox comicBookCheckBox;
    private JCheckBox labelCheckBox;
    private JTable comic_book_table;
    private JTable label_table;
    private JTextField cb_label_publisher_id;
    private JComboBox table_type_combo_box;
    private JButton loadButton;
    private JTextField curent_date_field;
    private JTextField stats_mon_start;
    private JTextField stats_mon_end;
    private JTextField cb_id;
    private JTextField label_label_id;
    public boolean comic_book_flag;
    public String comic_name, comic_format, comic_pub_date, comic_FOC, comic_label_id, comic_label_name, comic_description, label_description, comic_label_publisher_id, cur_date, start_mon_stats, end_mon_stats,comic_id,labels_id;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Comic book database");
        frame.setContentPane(new JavaCrud().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public JavaCrud() {


        cb_label.setEnabled(false);
        cb_label_description.setEnabled(false);
        cb_label_publisher_id.setEnabled(false);
        label_label_id.setEnabled(false);
        connect();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                comic_name = cb_name.getText();
                comic_format = (String)cb_format.getSelectedItem();
                comic_pub_date = cb_pub_date.getText();
                comic_FOC = cb_FOC.getText();
                comic_label_id = cb_label_id.getText();
                comic_description = cb_description.getText();
                comic_label_name = cb_label.getText();
                label_description = cb_label_description.getText();
                comic_label_publisher_id = cb_label_publisher_id.getText();
                try {

                    if((String)table_type_combo_box.getSelectedItem() == "Comic book") {

                        pst = con.prepareStatement("insert into comic_book(name, format, publishing_date, FOC, description, label_id) values(?,?,?,?,?,?)");

                        pst.setString(1, comic_name);
                        pst.setString(2, comic_format);
                        pst.setString(3, comic_pub_date);
                        pst.setString(4, comic_FOC);
                        pst.setString(5, comic_description);
                        pst.setString(6, comic_label_id);

                        pst.executeUpdate();
                        comic_book_table_load();
                    } else
                    {
                        pst = con.prepareStatement("insert into label(name, description, publisher_id) values(?,?,?)");

                        pst.setString(1, comic_label_name);
                        pst.setString(2, label_description);
                        pst.setString(3, comic_label_publisher_id);

                        pst.executeUpdate();
                        label_table_load();

                    }

                    JOptionPane.showMessageDialog(null, "Record added successfully");



                    if((String)table_type_combo_box.getSelectedItem() == "Comic book") {

                        cb_name.setText("");
                        cb_format.setSelectedIndex(0);
                        cb_pub_date.setText("");
                        cb_FOC.setText("");
                        cb_label_id.setText("");
                        cb_description.setText("");
                    } else
                    {
                        cb_label.setText("");
                        cb_label_description.setText("");
                        cb_label_publisher_id.setText("");
                    }

                }
                    catch (SQLException e1){
                            e1.printStackTrace();
                    }
            }
        });



        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                comic_name = cb_name.getText();
                comic_format = (String) cb_format.getSelectedItem();
                comic_pub_date = cb_pub_date.getText();
                comic_FOC = cb_FOC.getText();
                comic_label_id = cb_label_id.getText();
                comic_description = cb_description.getText();
                comic_id = cb_id.getText();

                labels_id = label_label_id.getText();
                comic_label_name = cb_label.getText();
                label_description = cb_label_description.getText();
                comic_label_publisher_id = cb_label_publisher_id.getText();

                try {


                    if((String)table_type_combo_box.getSelectedItem() == "Comic book") {
                        pst = con.prepareStatement("update comic_book set name = ?, format = ?, publishing_date = ?, FOC = ?, description = ?, label_id = ? where id = ?");

                        pst.setString(1, comic_name);
                        pst.setString(2, comic_format);
                        pst.setString(3, comic_pub_date);
                        pst.setString(4, comic_FOC);
                        pst.setString(5, comic_description);
                        pst.setString(6, comic_label_id);
                        pst.setString(7,comic_id);

                        pst.executeUpdate();
                        comic_book_table_load();
                    } else
                    {
                        pst = con.prepareStatement("update label set name = ?,  description = ?, publisher_id = ? where id = ?");

                        pst.setString(1, comic_label_name);
                        pst.setString(2, label_description);
                        pst.setString(3, comic_label_publisher_id);
                        pst.setString(4, labels_id);
                        pst.executeUpdate();
                        label_table_load();

                    }

                        JOptionPane.showMessageDialog(null, "Record updated successfully");


                    if((String)table_type_combo_box.getSelectedItem() == "Comic book") {

                        cb_name.setText("");
                        cb_format.setSelectedIndex(0);
                        cb_pub_date.setText("");
                        cb_FOC.setText("");
                        cb_label_id.setText("");
                        cb_description.setText("");
                    } else
                    {
                        cb_label.setText("");
                        cb_label_description.setText("");
                        cb_label_publisher_id.setText("");
                    }


                    }
                catch(SQLException e1){
                        e1.printStackTrace();
                    }


        }
        });

        //Удаление записи
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comic_name = cb_name.getText();
                comic_label_name = cb_label.getText();

                try {

                    if((String)table_type_combo_box.getSelectedItem() == "Comic book") {
                        pst = con.prepareStatement("delete from comic_book where name = ?");
                        pst.setString(1, comic_name);

                        pst.executeUpdate();
                        comic_book_table_load();
                    }
                    else
                    {
                        pst = con.prepareStatement("delete from label where name = ?");
                        pst.setString(1, comic_label_name);

                        pst.executeUpdate();
                        label_table_load();

                    }

                    JOptionPane.showMessageDialog(null, "Record deleted successfully");




                    if((String)table_type_combo_box.getSelectedItem() == "Comic book") {

                        cb_name.setText("");
                        cb_format.setSelectedIndex(0);
                        cb_pub_date.setText("");
                        cb_FOC.setText("");
                        cb_label_id.setText("");
                        cb_description.setText("");
                    } else
                    {
                        cb_label.setText("");
                        cb_label_description.setText("");
                        cb_label_publisher_id.setText("");
                    }

                }
                catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        });


        table_type_combo_box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((String)table_type_combo_box.getSelectedItem() == "Comic book")
                {
                    comic_book_flag = true;
                    cb_label.setEnabled(false);
                    cb_label_publisher_id.setEnabled(false);
                    cb_label_description.setEnabled(false);
                    label_label_id.setEnabled(false);


                    cb_name.setEnabled(true);
                    cb_FOC.setEnabled(true);
                    cb_pub_date.setEnabled(true);
                    cb_format.setEnabled(true);
                    cb_label_id.setEnabled(true);
                    cb_description.setEnabled(true);
                    cb_id.setEnabled(true);
                } else {
                    comic_book_flag = false;

                    cb_label.setEnabled(true);
                    cb_label_publisher_id.setEnabled(true);
                    cb_label_description.setEnabled(true);
                    label_label_id.setEnabled(true);

                    cb_name.setEnabled(false);
                    cb_FOC.setEnabled(false);
                    cb_pub_date.setEnabled(false);
                    cb_format.setEnabled(false);
                    cb_label_id.setEnabled(false);
                    cb_description.setEnabled(false);
                    cb_id.setEnabled(false);

                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label_table_load();
                comic_book_table_load();
            }
        });
        lastMonthReleasesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                future_comics();


            }
        });
        formatStatsInPeriodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                format_stats ();
            }
        });
    }

            Connection con;
            PreparedStatement pst;
    public void connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/comic_shop_web", "root", "root1");
            System.out.println("Success");
        } catch (ClassNotFoundException ex)
        {
                ex.printStackTrace();
        }
        catch (SQLException ex)
        {
                ex.printStackTrace();

        }
    }


    void comic_book_table_load()
    {
        try
        {
            pst = con.prepareStatement("select cb.`name`, cb.`format`, cb.`publishing_date`, cb.`FOC`, cb.`description`, L.`name` as `label`, P.`name` as `Publisher` from comic_book cb inner join label L on cb.`label_id` = L.`id` INNER JOIN `publisher` P ON L.`publisher_id` = P.`id`" );
            ResultSet rs = pst.executeQuery();
            comic_book_table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void label_table_load()
    {
        try
        {
            pst = con.prepareStatement("select L.`name` as `label`, P.`name` as `Publisher` from label L INNER JOIN `publisher` P ON L.`publisher_id` = P.`id`" );
            ResultSet rs1 = pst.executeQuery();
            label_table.setModel(DbUtils.resultSetToTableModel(rs1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void future_comics()
    {
        cur_date = curent_date_field.getText();
        try {
            String sqlq = "select cb.`name`, cb.`format`, cb.`publishing_date`, cb.`FOC`, cb.`description`, L.`name` as `label` from comic_book cb inner join label L on cb.`label_id` = L.`id` WHERE cb.publishing_date > '" + cur_date + "'";
            pst = con.prepareStatement(sqlq);
            ResultSet rs3 = pst.executeQuery();
            comic_book_table.setModel(DbUtils.resultSetToTableModel(rs3));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void format_stats (){
        start_mon_stats = stats_mon_start.getText();
        end_mon_stats = stats_mon_end.getText();
        try {
            String sqlqq = "select  format, count(format='tpb') FROM comic_book WHERE publishing_date between '"+start_mon_stats+"' and '"+end_mon_stats+"' GROUP BY format ORDER BY format";
            System.out.println(sqlqq);
            pst = con.prepareStatement(sqlqq);
            ResultSet rs4 = pst.executeQuery();
            comic_book_table.setModel(DbUtils.resultSetToTableModel(rs4));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}



