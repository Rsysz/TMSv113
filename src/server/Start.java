package server;

import client.SkillFactory;
import constants.ServerConstants;
import handling.MapleServerHandler;
import handling.channel.ChannelServer;
import handling.channel.MapleGuildRanking;
import handling.login.LoginServer;
import handling.cashshop.CashShopServer;
import handling.login.LoginInformationProvider;
import handling.world.World;
import java.sql.SQLException;
import database.DatabaseConnection;
import handling.world.family.MapleFamilyBuff;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import server.Timer.*;
import server.events.MapleOxQuizFactory;
import server.life.MapleLifeFactory;
import server.quest.MapleQuest;

public class Start {

    public final static void main(final String args[]) {
        final long originStartTime = System.currentTimeMillis();
        if (Boolean.parseBoolean(ServerProperties.getProperty("tms.Admin"))) {
            System.out.println("[!!! 管理員模式 !!!]");
        }
        if (Boolean.parseBoolean(ServerProperties.getProperty("tms.AutoRegister"))) {
            System.out.println("開啟註冊模式 :::");
        }
        try {
            final PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("UPDATE accounts SET loggedin = 0");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            throw new RuntimeException("[發生錯誤] 請檢查MySql連線." + System.lineSeparator() + ex.getLocalizedMessage());
        }
        if (System.getProperty("net.sf.odinms.wzpath") == null) { // auto initialize path by Windyboy
            System.setProperty("net.sf.odinms.wzpath", "wz");
        }
        World.init();
        WorldTimer.getInstance().start();
        EtcTimer.getInstance().start();
        MapTimer.getInstance().start();
        MobTimer.getInstance().start();
        CloneTimer.getInstance().start();
        EventTimer.getInstance().start();
        BuffTimer.getInstance().start();
        PingTimer.getInstance().start();
        LoginInformationProvider.getInstance();
        MapleQuest.initQuests();
        MapleLifeFactory.loadQuestCounts();
//        ItemMakerFactory.getInstance();
        MapleItemInformationProvider.getInstance().load();
        new Thread(() -> MapleItemInformationProvider.getInstance().loadStyles(false)).start();
        new Thread(() -> CashItemFactory.getInstance().initialize()).start();
        RandomRewards.getInstance();
        SkillFactory.initializeSkillInfo();
        MapleOxQuizFactory.getInstance().initialize();
        MapleCarnivalFactory.getInstance();
        MapleGuildRanking.getInstance().getRank();
        MapleFamilyBuff.getBuffEntry();
        //MapleServerHandler.registerMBean();
        RankingWorker.getInstance().run();
        MTSStorage.load();

        LoginServer.run_startup_configurations();
        ChannelServer.startChannel_Main();

        CashShopServer.run_startup_configurations();

        CheatTimer.getInstance().register(AutobanManager.getInstance(), 60000);
        Runtime.getRuntime().addShutdownHook(new Thread(ShutdownServer.getInstance()));
        SpeedRunner.getInstance().loadSpeedRuns();
        World.registerRespawn();
        LoginServer.setOn();
        System.out.println("載入完成 :::");
        System.out.println("啟動時間: " + tools.StringUtil.getReadableMillis(originStartTime, System.currentTimeMillis()));
        ChannelServer.loadEventScriptManager(); // 啟動完成後再載入活動
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.gc();
        PingTimer.getInstance().register(System::gc, 1800000);
    }

    public class Shutdown implements Runnable {

        @Override
        public void run() {
            new Thread(ShutdownServer.getInstance()).start();
        }
    }
}
