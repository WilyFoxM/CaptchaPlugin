package captchaplugin.captchaplugin.Utils;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Serializer {
    /**
     * Serializing Location object to string.
     *
     * @param  location  a Location object
     * @return  a serialized string
     */
    public static String stringFromLocation(Location location) {
        return location.getWorld().getName() + "#" + location.getX() + "#" + location.getY() + "#" + location.getZ() + "#" + location.getPitch() + "#" + location.getYaw() + "#";
    }

    /**
     * Deserializing String to Location object.
     *
     * @param  location  a serialized location string
     * @return  a deserialized Location object
     */
    public static Location locationFromString(String location) {
        String[] splitted = location.split("#");
        World world = Bukkit.getWorld(splitted[0]);
        double x = Double.parseDouble(splitted[1]);
        double y = Double.parseDouble(splitted[2]);
        double z = Double.parseDouble(splitted[3]);
        float pitch = Float.parseFloat(splitted[4]);
        float yaw = Float.parseFloat(splitted[5]);

        return new Location(world, x, y, z, pitch, yaw);
    }

    /**
     * Serializing Map object to string.
     *
     * @param  inv  a Map object
     * @return  a serialized string
     */
    public static String stringFromInv(Map<Integer, ItemStack> inv) {
        StringBuilder invString = new StringBuilder();

        for (Map.Entry<Integer, ItemStack> entry : inv.entrySet()) {
            invString.append(entry.getKey()).append("#").append(entry.getValue().getType()).append("#").append(entry.getValue().getAmount()).append("\n");
        }

        return invString.toString();
    }

    /**
     * Deserializing String to Map object.
     *
     * @param  invString  a serialized map string
     * @return  a deserialized Map object
     */
    public static Map<Integer, ItemStack> invFromString(String invString) {
        Map<Integer, ItemStack> inv = new HashMap<>();
        if (Objects.equals(invString, "") || invString.split("#").length < 1) return inv;

        for (String line : invString.split("\n")) {
            String[] splitted = line.split("#");
            Integer key = Integer.parseInt(splitted[0]);
            Material material = Material.getMaterial(splitted[1]);
            int amount = Integer.parseInt(splitted[2]);
            ItemStack item = new ItemStack(material, amount);
            inv.put(key, item);
        }

        return inv;
    }
}
