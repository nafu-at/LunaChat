/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2013
 */
package com.github.ucchyocean.lc;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * LunaChatロガー
 * @author ucchy
 */
public class LunaChatLogger {

    private static final SimpleDateFormat lformat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dformat =
            new SimpleDateFormat("yyyy-MM-dd");
    
    private File file;
    private String dirPath;
    private String name;
    
    /**
     * コンストラクタ
     * @param name ログ名
     */
    public LunaChatLogger(String name) {
        
        this.name = name;
        checkDir();
    }
    
    /**
     * ログを出力する
     * @param message ログ内容
     */
    public void log(String message) {
        
        checkDir();
        
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true);
            String str = lformat.format(new Date()) + "," + message;
            writer.write(str + "\r\n");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ( writer != null ) {
                try {
                    writer.close();
                } catch (Exception e) {
                    // do nothing.
                }
            }
        }
    }
    
    /**
     * ログの出力先フォルダをチェックし、変更されるなら更新する。
     */
    private void checkDir() {
        
        String temp = LunaChat.instance.getDataFolder() + File.separator +
                "logs" + File.separator + dformat.format(new Date());
                
        if ( temp.equals(dirPath) ) {
            return;
        }
        dirPath = temp;
        
        File dir = new File(dirPath);
        if ( !dir.exists() || !dir.isDirectory() ) {
            dir.mkdirs();
        }
        
        file = new File(dir, name + ".log");
    }
}