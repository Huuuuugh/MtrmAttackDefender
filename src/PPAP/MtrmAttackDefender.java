package PPAP;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MtrmAttackDefender extends JavaPlugin{
    ProtocolManager pm;
    @Override
    public void onEnable(){
        pm=ProtocolLibrary.getProtocolManager();
        getLogger().info("PayloadCathcer 已加载");
        init();

    }
    public void init(){
        final PacketAdapter PacketListener = new PacketAdapter(this, ListenerPriority.NORMAL, new PacketType[] { PacketType.Play.Client.CUSTOM_PAYLOAD }) {
            public void onPacketReceiving(PacketEvent packetEvent) {
                //System.out.println("收到包" + packetEvent.getPlayer());

                    if(packetEvent.getPacket().getStrings().getValues().get(0).equals("MTRM")){
                        if(packetEvent.getPlayer().isOp()){
                            System.out.println("[MTRM拦截]"+packetEvent.getPlayer().getName()+"跳过检查");
                        }else{
                            packetEvent.setCancelled(true);
                            System.out.println("[PPAP]MTRM发包已取消");
                            Bukkit.broadcastMessage("[PPAP]"+packetEvent.getPlayer().getName()+"试图用MTRM发包！已封禁");
                            packetEvent.getPlayer().setBanned(true);
                            packetEvent.getPlayer().kickPlayer("该操作不可用！");
                            Bukkit.getServer().banIP(packetEvent.getPlayer().getName());
                        }

                    }

            }
        };
        pm.addPacketListener(PacketListener);
    }
/*    @Override
    public void onPacketReceiving(PacketEvent e) {
        System.out.println("收到包"+e.getPlayer());
        if(e.getPlayer().getName().contains("PPAP")||e.getPlayer().getName().contains("jixie")){
            System.out.println("[PPAP]:"+e.getPacket());
            System.out.println("\n\n\n\n\n\n\n");
            System.out.println("[PPAP]"+e.getPacket().getBytes());
        }

    }*/

}
