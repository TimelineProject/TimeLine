import database.DatabaseConnect;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.*;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class RegisterTest {

    WebDriver driver;

    @Before
    public void init(){

        System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/Register.jsp");
    }

    @Test
    public void test_register_to_login(){
        WebElement registerBtn = driver.findElement(By.className("goLogin"));
        registerBtn.click();
        assertThat(driver.getTitle(),equalTo("Login"));
    }

    @Test
    public void test_register_success(){

        WebElement userBox = driver.findElement(By.name("reg_username"));
        WebElement psdBox1 = driver.findElement(By.name("reg_password1"));
        WebElement psdBox2 = driver.findElement(By.name("reg_password2"));
        userBox.sendKeys("br");
        psdBox1.sendKeys("br");
        psdBox2.sendKeys("br");
        WebElement loginBtn = driver.findElement(By.className("register"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("注册成功！"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Register"));
        PreparedStatement pstmt = null;
        Connection connection = null;
        try {
            connection = DatabaseConnect.conn();
            String sql1 = "select * from users where account = 'br'";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql1);
            rs.next();
            assertThat(rs.getString("account"),equalTo("br"));
            assertThat(rs.getString("password"),equalTo("br"));
            rs.close();
            stmt.close();
            String sql2 = "delete from infos where account = 'br';";
            pstmt = connection.prepareStatement(sql2);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void test_register_success_with_chinese() throws SQLException {

        WebElement userBox = driver.findElement(By.name("reg_username"));
        WebElement psdBox1 = driver.findElement(By.name("reg_password1"));
        WebElement psdBox2 = driver.findElement(By.name("reg_password2"));
        userBox.sendKeys("符嘉铭");
        psdBox1.sendKeys("fjm");
        psdBox2.sendKeys("fjm");
        WebElement loginBtn = driver.findElement(By.className("register"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("注册成功！"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Register"));
        PreparedStatement pstmt = null;
        Connection connection = null;
        try {
            connection = DatabaseConnect.conn();
            String sql1 = "select * from users where account = '符嘉铭'";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql1);
            rs.next();
            assertThat(rs.getString("account"),equalTo("符嘉铭"));
            assertThat(rs.getString("password"),equalTo("fjm"));
            rs.close();
            stmt.close();
            String sql2 = "delete from infos where account = '符嘉铭';";
            pstmt = connection.prepareStatement(sql2);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test_register_success_with_special_character(){

        WebElement userBox = driver.findElement(By.name("reg_username"));
        WebElement psdBox1 = driver.findElement(By.name("reg_password1"));
        WebElement psdBox2 = driver.findElement(By.name("reg_password2"));
        userBox.sendKeys("@$###");
        psdBox1.sendKeys("no");
        psdBox2.sendKeys("no");
        WebElement loginBtn = driver.findElement(By.className("register"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("注册成功！"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Register"));
        PreparedStatement pstmt = null;
        Connection connection = null;
        try {
            connection = DatabaseConnect.conn();
            String sql1 = "select * from users where account = '@$###'";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql1);
            rs.next();
            assertThat(rs.getString("account"),equalTo("@$###"));
            assertThat(rs.getString("password"),equalTo("no"));
            rs.close();
            stmt.close();
            String sql2 = "delete from infos where account = '@$###';";
            pstmt = connection.prepareStatement(sql2);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test_register_failed_of_first_kind_1() throws SQLException {

        WebElement userBox = driver.findElement(By.name("reg_username"));
        WebElement psdBox1 = driver.findElement(By.name("reg_password1"));
        WebElement psdBox2 = driver.findElement(By.name("reg_password2"));
        userBox.sendKeys("fjm");
        psdBox1.sendKeys("fjm");
        psdBox2.sendKeys("fjm");
        Connection connection = JdbcUtil.getCon();
        String sql = "select * from users where account = 'fjm'";
        Statement state = connection.createStatement();
        ResultSet rs = state.executeQuery(sql);
        assertNotNull(rs.next());
        connection.close();
        WebElement loginBtn = driver.findElement(By.className("register"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("用户名已存在！"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Register"));

    }

    @Test
    public void test_register_failed_of_second_kind_1(){

        WebElement userBox = driver.findElement(By.name("reg_username"));
        WebElement psdBox1 = driver.findElement(By.name("reg_password1"));
        WebElement psdBox2 = driver.findElement(By.name("reg_password2"));
        userBox.sendKeys("");
        psdBox1.sendKeys("");
        psdBox2.sendKeys("");
        WebElement loginBtn = driver.findElement(By.className("register"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("任何一项不能为空！"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Register"));

    }

    @Test
    public void test_register_failed_of_second_kind_2(){

        WebElement userBox = driver.findElement(By.name("reg_username"));
        WebElement psdBox1 = driver.findElement(By.name("reg_password1"));
        WebElement psdBox2 = driver.findElement(By.name("reg_password2"));
        userBox.sendKeys("fjm");
        psdBox1.sendKeys("");
        psdBox2.sendKeys("");
        WebElement loginBtn = driver.findElement(By.className("register"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("任何一项不能为空！"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Register"));

    }

    @Test
    public void test_register_failed_of_second_kind_3(){

        WebElement userBox = driver.findElement(By.name("reg_username"));
        WebElement psdBox1 = driver.findElement(By.name("reg_password1"));
        WebElement psdBox2 = driver.findElement(By.name("reg_password2"));
        userBox.sendKeys("");
        psdBox1.sendKeys("fjm");
        psdBox2.sendKeys("");
        WebElement loginBtn = driver.findElement(By.className("register"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("任何一项不能为空！"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Register"));

    }

    @Test
    public void test_register_failed_of_second_kind_4(){

        WebElement userBox = driver.findElement(By.name("reg_username"));
        WebElement psdBox1 = driver.findElement(By.name("reg_password1"));
        WebElement psdBox2 = driver.findElement(By.name("reg_password2"));
        userBox.sendKeys("");
        psdBox1.sendKeys("");
        psdBox2.sendKeys("fjm");
        WebElement loginBtn = driver.findElement(By.className("register"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("任何一项不能为空！"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Register"));

    }

    @Test
    public void test_register_failed_of_second_kind_5(){

        WebElement userBox = driver.findElement(By.name("reg_username"));
        WebElement psdBox1 = driver.findElement(By.name("reg_password1"));
        WebElement psdBox2 = driver.findElement(By.name("reg_password2"));
        userBox.sendKeys("fjm");
        psdBox1.sendKeys("");
        psdBox2.sendKeys("fjm");
        WebElement loginBtn = driver.findElement(By.className("register"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("任何一项不能为空！"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Register"));

    }

    @Test
    public void test_register_failed_of_second_kind_6(){

        WebElement userBox = driver.findElement(By.name("reg_username"));
        WebElement psdBox1 = driver.findElement(By.name("reg_password1"));
        WebElement psdBox2 = driver.findElement(By.name("reg_password2"));
        userBox.sendKeys("fjm");
        psdBox1.sendKeys("fjm");
        psdBox2.sendKeys("");
        WebElement loginBtn = driver.findElement(By.className("register"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("任何一项不能为空！"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Register"));

    }

    @Test
    public void test_register_failed_of_second_kind_7(){

        WebElement userBox = driver.findElement(By.name("reg_username"));
        WebElement psdBox1 = driver.findElement(By.name("reg_password1"));
        WebElement psdBox2 = driver.findElement(By.name("reg_password2"));
        userBox.sendKeys("");
        psdBox1.sendKeys("fjm");
        psdBox2.sendKeys("fjm");
        WebElement loginBtn = driver.findElement(By.className("register"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("任何一项不能为空！"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Register"));

    }



    @Test
    public void test_register_failed_of_third_kind_1(){

        WebElement userBox = driver.findElement(By.name("reg_username"));
        WebElement psdBox1 = driver.findElement(By.name("reg_password1"));
        WebElement psdBox2 = driver.findElement(By.name("reg_password2"));
        userBox.sendKeys("fgl");
        psdBox1.sendKeys("fgl");
        psdBox2.sendKeys("fgl1");
        WebElement loginBtn = driver.findElement(By.className("register"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("两次密码不一致！"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Register"));

    }


    @After
    public void end(){
        driver.quit();

    }
}
