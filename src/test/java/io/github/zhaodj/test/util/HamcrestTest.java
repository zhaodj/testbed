package io.github.zhaodj.test.util;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

public class HamcrestTest {

    @Test
    public void testEqual(){
        Integer x = null;
        Integer y = 1;
        Assert.assertTrue(x == y);
        Assert.assertEquals(x, y);
        Assert.assertThat(x, both(not(equalTo(y))).and(notNullValue()));
    }

    @Test(expected = RuntimeException.class)
    public void testException(){
        String test = null;
        test.length();
    }

}
