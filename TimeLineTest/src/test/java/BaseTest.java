
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.xml.sax.InputSource;

public class BaseTest {
    public static IDatabaseConnection connection;
    public static DataSource dataSource;
    private File temp;

    @BeforeClass
    public static void init() throws Exception {

        // 通过DataSource获取DataBaseSourceConnection
        connection = new DatabaseDataSourceConnection(dataSource);
    }

    @AfterClass
    public static void destroy() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    protected IDataSet getDataSet(String name) throws DataSetException {
        // 通过类加载器获取default-data.xml文件的内容
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(name + ".xml");

        // IDataSet就类似是一个数据的容器,把default-data.xml内容
        // 转换成了DBUnit可识别的数据返回出去
        return new FlatXmlDataSet(new FlatXmlProducer(new InputSource(is)));
    }

    protected void backupAll() throws Exception {
        // createDataSet代表从数据库中获取到DataSet,此时DataSet为数据库的内容
        IDataSet ds = connection.createDataSet();

        // 创建临时文件
        temp = File.createTempFile("temp", "xml");

        // 将数据库内容的DataSet写入临时文件当中
        FlatXmlDataSet.write(ds, new FileWriter(temp), "UTF-8");
    }

    protected void backupCustom(String... tableName) throws Exception {
        // 此种形式能保存某几张表的的数据,而不需要全部备份
        QueryDataSet qds = new QueryDataSet(connection);
        for (String str : tableName) {
            qds.addTable(str);
        }
        temp = File.createTempFile("temp", "xml");
        FlatXmlDataSet.write(qds, new FileWriter(temp), "UTF-8");
    }

    protected void recover() throws Exception {

        // 获取数据库内容的临时文件生成DataSet
        IDataSet ds = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(
                new FileInputStream(temp))));

        // 使用DatabaseOperation的枚举方法先清空数据库内容再还原数据库
        DatabaseOperation.CLEAN_INSERT.execute(connection, ds);
    }
}
