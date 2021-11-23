package unit_test.file;

import com.teros.ext.common.file.CommonFile;
import org.junit.jupiter.api.Test;

public class CommonFileTest {

    CommonFile commonFile = new CommonFile();

    @Test
    public void TestFileExist()
    {
        boolean ret = commonFile.fileExist("e:/test222.txt");
        System.out.println(ret);
    }
}
