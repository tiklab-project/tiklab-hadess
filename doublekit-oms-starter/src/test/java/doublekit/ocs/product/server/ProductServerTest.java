package doublekit.ocs.product.server;

import com.doublekit.product.OmsApplication;
import com.doublekit.product.product.model.Product;
import com.doublekit.product.product.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@SpringBootTest(classes = OmsApplication.class)
@RunWith(SpringRunner.class)
public class ProductServerTest {

    @Autowired
    ProductService productService;

    @Test
    public void findProductAll(){
        List<Product> allProduct = productService.findAllProduct();
        assertNotNull(allProduct);
    }

    @Test
    public void findProductOne(){
        Product one = productService.findOne("1");
        assertNotNull(one);
    }
}
