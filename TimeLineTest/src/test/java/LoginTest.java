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

public class LoginTest {

    WebDriver driver;

    @Before
    public void init(){

        System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/Login.jsp");
    }

    @Test
    public void test_login_to_register(){
        WebElement registerBtn = driver.findElement(By.className("register"));
        registerBtn.click();
        assertThat(driver.getTitle(),equalTo("Register"));
    }

    @Test
    public void test_login_success() throws SQLException {

        WebElement userBox = driver.findElement(By.name("lgn_username"));
        WebElement psdBox = driver.findElement(By.name("lgn_password"));
        userBox.sendKeys("fjm");
        psdBox.sendKeys("fjm");
        Connection connection = JdbcUtil.getCon();
        String sql = "select * from users where account = 'fjm' and password = 'fjm'";
        Statement state = connection.createStatement();
        ResultSet rs = state.executeQuery(sql);
        assertNotNull(rs.next());
        connection.close();
        WebElement loginBtn = driver.findElement(By.className("login"));
        loginBtn.click();
        assertThat(driver.getTitle(),equalTo("TimeLine"));
    }

    @Test
    public void test_login_failed_of_first_kind_1() throws SQLException {
        WebElement userBox = driver.findElement(By.name("lgn_username"));
        WebElement psdBox = driver.findElement(By.name("lgn_password"));
        userBox.sendKeys("fjm1");
        psdBox.sendKeys("fjm");
        Connection connection = JdbcUtil.getCon();
        String sql = "select * from users where account = 'fjm1' and password = 'fjm'";
        Statement state = connection.createStatement();
        ResultSet rs = state.executeQuery(sql);
        assertThat(rs.next(),equalTo(false));
        connection.close();
        WebElement loginBtn = driver.findElement(By.className("login"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("用户名不存在或密码错误!"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Login"));
    }

    @Test
    public void test_login_failed_of_first_kind_2() throws SQLException {
        WebElement userBox = driver.findElement(By.name("lgn_username"));
        WebElement psdBox = driver.findElement(By.name("lgn_password"));
        userBox.sendKeys("fjm");
        psdBox.sendKeys("fjm1");
        Connection connection = JdbcUtil.getCon();
        String sql = "select * from users where account = 'fjm' and password = 'fjm1'";
        Statement state = connection.createStatement();
        ResultSet rs = state.executeQuery(sql);
        assertThat(rs.next(),equalTo(false));
        connection.close();
        WebElement loginBtn = driver.findElement(By.className("login"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("用户名不存在或密码错误!"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Login"));
    }

    @Test
    public void test_login_failed_of_first_kind_3() throws SQLException {
        WebElement userBox = driver.findElement(By.name("lgn_username"));
        WebElement psdBox = driver.findElement(By.name("lgn_password"));
        userBox.sendKeys("fjm1");
        psdBox.sendKeys("fjm1");
        Connection connection = JdbcUtil.getCon();
        String sql = "select * from users where account = 'fjm1' and password = 'fjm1'";
        Statement state = connection.createStatement();
        ResultSet rs = state.executeQuery(sql);
        assertThat(rs.next(),equalTo(false));
        connection.close();
        WebElement loginBtn = driver.findElement(By.className("login"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("用户名不存在或密码错误!"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Login"));
    }

    @Test
    public void test_login_failed_of_second_kind_1(){
        WebElement userBox = driver.findElement(By.name("lgn_username"));
        WebElement psdBox = driver.findElement(By.name("lgn_password"));
        userBox.sendKeys("");
        psdBox.sendKeys("");
        WebElement loginBtn = driver.findElement(By.className("login"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("请输入用户名和密码!"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Login"));
    }

    @Test
    public void test_login_failed_of_second_kind_2(){
        WebElement userBox = driver.findElement(By.name("lgn_username"));
        WebElement psdBox = driver.findElement(By.name("lgn_password"));
        userBox.sendKeys("fjm");
        psdBox.sendKeys("");
        WebElement loginBtn = driver.findElement(By.className("login"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("请输入用户名和密码!"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Login"));
    }

    @Test
    public void test_login_failed_of_second_kind_3(){
        WebElement userBox = driver.findElement(By.name("lgn_username"));
        WebElement psdBox = driver.findElement(By.name("lgn_password"));
        userBox.sendKeys("");
        psdBox.sendKeys("fjm");
        WebElement loginBtn = driver.findElement(By.className("login"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("请输入用户名和密码!"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Login"));
    }

    @Test
    public void test_login_with_length(){
        WebElement userBox = driver.findElement(By.name("lgn_username"));
        WebElement psdBox = driver.findElement(By.name("lgn_password"));
        userBox.sendKeys("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        psdBox.sendKeys("fjm");
        WebElement loginBtn = driver.findElement(By.className("login"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("用户名不存在或密码错误!"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Login"));
    }

    @Test
    public void test_login_with_special_character(){
        WebElement userBox = driver.findElement(By.name("lgn_username"));
        WebElement psdBox = driver.findElement(By.name("lgn_password"));
        userBox.sendKeys("!@#$%^&*()_+=-[]{}|/?><,.;:、");
        psdBox.sendKeys("fjm1");
        WebElement loginBtn = driver.findElement(By.className("login"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("用户名不存在或密码错误!"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Login"));
    }

    @Test
    public void test_login_with_SQL_injection(){
        WebElement userBox = driver.findElement(By.name("lgn_username"));
        WebElement psdBox = driver.findElement(By.name("lgn_password"));
        userBox.sendKeys("' or 1=1#");
        psdBox.sendKeys("11");
        WebElement loginBtn = driver.findElement(By.className("login"));
        loginBtn.click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText(),equalTo("用户名不存在或密码错误!"));
        alert.accept();
        assertThat(driver.getTitle(),equalTo("Login"));
    }



    @After
    public void end(){
        driver.quit();
    }
}
