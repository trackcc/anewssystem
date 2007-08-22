
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.mysql.MySqlConnection;
import org.dbunit.dataset.csv.CsvDataSetWriter;
import org.dbunit.operation.DatabaseOperation;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dbunit.dataset.*;
import org.dbunit.dataset.datatype.DataType;

import java.io.*;

public class Test {

    public static void main(String[] args) throws Exception {
        IDatabaseConnection con;
        con = getMysql();
        toExcel(con);
        con.close();

        //IDataSet dataset = new XmlDataSet(new FileInputStream("export.xml"));
        //con = getHsqldb();
        //DatabaseOperation.CLEAN_INSERT.execute(con, dataset);
        //con.close();

        //con = getHsqldb();
        //toExcel(con);
        //con.close();
    }

    static IDatabaseConnection getHsqldb() throws Exception {
        // hsqldb
        Class.forName("org.hsqldb.jdbcDriver");
        Connection jdbcConnection = DriverManager
            .getConnection("jdbc:hsqldb:file:db/test;shutdown=true", "sa", "");
        IDatabaseConnection con = new DatabaseConnection(jdbcConnection);
        return con;
    }

    static IDatabaseConnection getMssql() throws Exception {
        // sql server
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        Connection jdbcConnection = DriverManager
            .getConnection("jdbc:jtds:sqlserver://192.168.0.6:1433/ze;SelectMethod=cursor", "sa", "");
        IDatabaseConnection con = new DatabaseConnection(jdbcConnection);
        return con;
    }

    static IDatabaseConnection getMysql() throws Exception {
        // mysql
        Class.forName("com.mysql.jdbc.Driver");
        Connection jdbcConnection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/b2b?useUnicode=true&characterEncoding=UTF8", "root", "");
        IDatabaseConnection con = new MySqlConnection(jdbcConnection, "b2b");
        return con;
    }

    static void toXml(IDatabaseConnection con) throws Exception {
        IDataSet dataset = con.createDataSet();
        toXml(dataset);
    }
    static void toXml(IDataSet dataset) throws Exception {
        XmlDataSet.write(dataset, new FileOutputStream("export.xml"));
    }

    static void toExcel(IDatabaseConnection con) throws Exception {
        IDataSet dataset = con.createDataSet();
        toExcel(dataset);
    }
    static void toExcel(IDataSet dataset) throws Exception {
        FileOutputStream out = new FileOutputStream("export.xls");
        XlsDataSet.write(dataset, out);
        out.close();
    }

    static void toCsv(IDatabaseConnection con) throws Exception {
        IDataSet dataset = con.createDataSet();
        toCsv(dataset);
    }
    static void toCsv(IDataSet dataset) throws Exception {
        CsvDataSetWriter.write(dataset, new File("export"));
    }
}