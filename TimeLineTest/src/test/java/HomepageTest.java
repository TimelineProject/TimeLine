import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class HomepageTest {

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
    }

    @Test
    public void test_refresh(){
        WebElement refreshBtn = driver.findElement(By.className("refresh"));
        refreshBtn.click();
        assertThat(driver.getTitle(),equalTo("TimeLine"));
    }

    @Test
    public void test_homepage_to_publish(){
        WebElement publishBtn = driver.findElement(By.className("publish"));
        publishBtn.click();
        assertThat(driver.getTitle(),equalTo("Publish"));
    }

    @Test
    public void test_information() throws SQLException {
        int flag = 0;
        List<WebElement> information = driver.findElements(By.xpath("//table[@class='message']/tr"));
        Connection conn = JdbcUtil.getCon();
        String sql = "select * from infos natural join users order by time desc";
        Statement state = conn.createStatement();
        ResultSet rs = state.executeQuery(sql);
        for(WebElement row : information){
            if(flag % 2 == 0){
                assertNotNull(rs.next());
                WebElement col1 = row.findElement(By.xpath("//td[1]"));
                String mes = "作者：" + rs.getString("account") +
                        ";&emsp;&emsp;\n" +
                        "            &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;发布时间：" +
                        rs.getString("time");
                assertThat(mes,equalTo(col1.getText()));
            }else{
                WebElement col1 = row.findElement(By.xpath("//td[1]"));
                assertThat(rs.getString("information"),equalTo(col1.getText()));
                if(!rs.getString("image").equals("")) {
                    WebElement col2 = row.findElement(By.xpath("//td[2]"));
                    String pic = "/image/" + rs.getString("image");
                    assertThat(pic,equalTo(col2.getAttribute("src")));
                }
            }
            flag++;
        }
    }

    @After
    public void end(){
        driver.quit();
    }

}
