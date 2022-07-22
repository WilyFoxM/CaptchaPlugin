package captchaplugin.captchaplugin.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class MapUtils {
    /**
     * Getting image that must be 125x125 pixels and drawing it on map.
     *
     * @param  url  a direct URL to image
     * @return  a filled with image map
     */
    public static ItemStack getFilledMap(String url) {
        ItemStack map = new ItemStack(Material.FILLED_MAP, 1);
        MapMeta meta = (MapMeta) map.getItemMeta();
        try {
            BufferedImage image = ImageIO.read(new URL(url)); //should be initialized outside the render method
            MapView view = Bukkit.createMap(Bukkit.getWorld("world"));
            view.setScale(MapView.Scale.CLOSEST);
            view.getRenderers().clear();
            view.addRenderer(new MapRenderer() {
                public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
                    mapCanvas.drawImage(0, 0, image);
                }
            });
            meta.setMapView(view);
        } catch(IOException e) {
            Bukkit.getLogger().warning(e.getMessage());
        }
        meta.setDisplayName(ChatColor.MAGIC + "VALIDATION");
        map.setItemMeta(meta);
        return map;
    }
}
