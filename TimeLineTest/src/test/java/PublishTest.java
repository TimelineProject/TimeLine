import database.DatabaseConnect;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class PublishTest {

    WebDriver driver;

    @Before
    public void init(){

        System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/Login.jsp");
        WebElement userBox = driver.findElement(By.name("lgn_username"));
        WebElement psdBox = driver.findElement(By.name("lgn_password"));
        userBox.sendKeys("fjm");
        psdBox.sendKeys("fjm");
        WebElement loginBtn = driver.findElement(By.className("login"));
        loginBtn.click();
        WebElement publishBtn = driver.findElement(By.className("publish"));
        publishBtn.click();
    }

    @Test
    public void test_publish_to_homepage(){
        WebElement homepageBtn = driver.findElement(By.name("cmdread"));
        homepageBtn.click();
        assertThat(driver.getTitle(),equalTo("TimeLine"));
    }

    @Test
    public void test_add_to_timeline_with_no_message(){
        WebElement message = driver.findElement(By.name("mes_information"));
        message.sendKeys("");
        WebElement publishBtn = driver.findElement(By.name("cmdok"));
        publishBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("请输入信息!"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Publish"));
    }

    @Test
    public void test_add_to_timeline_in_success(){
        WebElement message = driver.findElement(By.name("mes_information"));
        message.sendKeys("Hello!");
        WebElement publishBtn = driver.findElement(By.name("cmdok"));
        publishBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("发布成功!"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Publish"));
    }

    @Test
    public void test_add_to_timeline_success_in_database() throws SQLException {
        WebElement message = driver.findElement(By.name("mes_information"));
        message.sendKeys("Hi!");
        WebElement publishBtn = driver.findElement(By.name("cmdok"));
        publishBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("发布成功!"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Publish"));
        Connection connection = null;
        connection = DatabaseConnect.conn();
        String sql1 = "select * from infos natural join users where account = 'fjm' order by time desc";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql1);
        rs.next();
        assertThat(rs.getString("information"),equalTo("Hi!"));
        rs.close();
        stmt.close();
        connection.close();
    }

    @Test
    public void test_add_to_timeline_in_success_in_homepage(){
        WebElement message = driver.findElement(By.name("mes_information"));
        message.sendKeys("Hello!");
        WebElement publishBtn = driver.findElement(By.name("cmdok"));
        publishBtn.click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        WebElement homepageBtn = driver.findElement(By.name("cmdread"));
        homepageBtn.click();

    }

    @Test
    public void test_add_picture() throws SQLException {
        WebElement picture = driver.findElement(By.name("mes_image"));
        picture.sendKeys("D:/学习/我的学习资料/愤怒的小鸟材料/愤怒的小鸟/Source/Image/BIRDS_1.png");
        WebElement uploadBtn = driver.findElement(By.name("add_image"));
        java.util.Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format( now );
        uploadBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("上传成功!"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Publish"));
        File file = new File("D:/软件/IDEA/Workplace/TimeLineTest/image");
        String img[] = file.list();
        String pictureName = img[0];
        Connection connection = JdbcUtil.getCon();
        String sql = "select * from infos natural join users where account = 'fjm' and image = '" +  pictureName + "' and time > '" + time + "'";
        Statement state = connection.createStatement();
        ResultSet rs = state.executeQuery(sql);
        assertThat(rs.next(),equalTo(false));
        connection.close();
    }

    @Test
    public void test_add_picture_and_message_with_success() throws SQLException {
        WebElement picture = driver.findElement(By.name("mes_image"));
        picture.sendKeys("D:/学习/我的学习资料/愤怒的小鸟材料/愤怒的小鸟/Source/Image/BIRDS_1.png");
        WebElement uploadBtn = driver.findElement(By.name("add_image"));
        uploadBtn.click();
        File file = new File("D:/软件/IDEA/Workplace/TimeLineTest/image");
        Alert alert = driver.switchTo().alert();
        alert.accept();
        WebElement message = driver.findElement(By.name("mes_information"));
        message.sendKeys("大家好！");
        WebElement publishBtn = driver.findElement(By.name("cmdok"));
        publishBtn.click();
        alert = driver.switchTo().alert();
        alert.accept();
        Connection connection = JdbcUtil.getCon();
        String sql = "select * from infos natural join users where account = 'fjm' order by time desc";
        Statement state = connection.createStatement();
        ResultSet rs = state.executeQuery(sql);
        assertThat(rs.next(),equalTo(true));
        WebElement homepageBtn = driver.findElement(By.name("cmdread"));
        homepageBtn.click();

        //不知道为什么我绝对路径不行，用//tr[2]//td[1]他一直抓取的是//tr[1]//td[1]的内容

        List<WebElement> information = driver.findElements(By.xpath("//table[@class='message']/tr"));
        int flag = 0;
        for(WebElement row : information) {
            if (flag % 2 == 0 && flag < 2) {
                assertNotNull(rs.next());
                WebElement col1 = row.findElement(By.xpath("//td[1]"));
                String mes = "作者：" + rs.getString("account") +
                        ";&emsp;&emsp;\n" +
                        "            &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;发布时间：" +
                        rs.getString("time");
                assertThat(mes, equalTo(col1.getText()));
            } else {
                WebElement col1 = row.findElement(By.xpath("//td[1]"));
                assertThat(rs.getString("information"), equalTo(col1.getText()));
                if (!rs.getString("image").equals("")) {
                    WebElement col2 = row.findElement(By.xpath("//td[2]"));
                    String pic = "/image/" + rs.getString("image");
                    assertThat(pic, equalTo(col2.getAttribute("src")));
                }
            }
            flag++;
        }
        connection.close();
    }

    @After
    public void end(){
        driver.quit();
    }
}
