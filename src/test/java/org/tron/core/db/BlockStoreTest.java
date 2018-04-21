package org.tron.core.db;

import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.tron.common.utils.FileUtil;
import org.tron.core.Constant;
import org.tron.core.config.DefaultConfig;
import org.tron.core.config.args.Args;

@Slf4j
public class BlockStoreTest {

  private static final String dbPath = "output-blockStore-test";
  BlockStore blockStore;
  private static AnnotationConfigApplicationContext context;

  static {
    Args.setParam(new String[]{"--output-directory", dbPath},
        Constant.TEST_CONF);
    context = new AnnotationConfigApplicationContext(DefaultConfig.class);
  }

  @Before
  public void init() {
    blockStore = context.getBean(BlockStore.class);
  }

  @After
  public void destroy() {
    Args.clearParam();
    FileUtil.deleteDir(new File(dbPath));
    context.destroy();

  }

  @Test
  public void testCreateBlockStore() {

    Assert.assertEquals("0000000000000000000000000000000000000000000000000000000000000000",
        blockStore.getHeadBlockId().toString());
    Assert.assertEquals(0, blockStore.getHeadBlockNum());
    Assert.assertEquals(blockStore.getGenesisTime(), blockStore.getHeadBlockTime());
    Assert.assertEquals(0, blockStore.currentASlot());

  }
}
