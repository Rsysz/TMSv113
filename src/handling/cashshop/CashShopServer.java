/*
 This file is part of the OdinMS Maple Story Server
 Copyright (C) 2008 ~ 2010 Patrick Huy <patrick.huy@frz.cc> 
 Matthias Butz <matze@odinms.de>
 Jan Christian Meyer <vimes@odinms.de>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License version 3
 as published by the Free Software Foundation. You may not use, modify
 or distribute this program under any other version of the
 GNU Affero General Public License.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package handling.cashshop;

import java.net.InetSocketAddress;

import handling.channel.PlayerStorage;
import handling.netty.ServerConnection;

import server.MTSStorage;
import server.ServerProperties;

public class CashShopServer {

    private static ServerConnection init;
    private static String ip;
    private  static int PORT = 8596;
    private static PlayerStorage players, playersMTS;
    private static boolean finishedShutdown = false;

    public static final void run_startup_configurations() {
        PORT = ServerProperties.getIntProperty("tms.CPort", "8596");
        ip = ServerProperties.getProperty("tms.IP") + ":" + PORT;
        try {
            init = new ServerConnection(PORT, 1, -10);//could code world here to seperate them
            init.run();
            players = new PlayerStorage(-10);
            playersMTS = new PlayerStorage(-20);
            System.out.println("CashShop : Listening on port " + PORT);
        } catch (final Exception e) {
            System.err.println("綁定端口 " + PORT + " 失敗");
            e.printStackTrace();
            throw new RuntimeException("綁定失敗.", e);
        }
    }

    public static final String getIP() {
        return ip;
    }

    public static final PlayerStorage getPlayerStorage() {
        return players;
    }

    public static final PlayerStorage getPlayerStorageMTS() {
        return playersMTS;
    }

    public static final void shutdown() {
        if (finishedShutdown) {
            return;
        }
        System.out.println("Saving all connected clients (CS)...");
        players.disconnectAll();
        playersMTS.disconnectAll();
        MTSStorage.getInstance().saveBuyNow(true);
        System.out.println("Shutting down CS...");
        //acceptor.unbindAll();
        finishedShutdown = true;
    }

    public static boolean isShutdown() {
        return finishedShutdown;
    }
}
