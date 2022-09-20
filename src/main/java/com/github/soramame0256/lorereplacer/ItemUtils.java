package com.github.soramame0256.lorereplacer;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ItemUtils {
    public static String getHash(List<String> sl){
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(md5 == null) return "NULL";
        StringBuilder tot = new StringBuilder();
        for(String s : sl){
            tot.append(s);
        }
        byte[] hash = md5.digest(tot.toString().getBytes());
        return String.format("%020x", new BigInteger(1, hash));
    }
//    private static String setHash(ItemStack e, List<String> sl){
//        if(e.getTagCompound() == null) return "NULL";
//        NBTTagCompound nbt = new NBTTagCompound();
//        String hash = getHash(sl);
//        nbt.setString("hash", hash);
//        e.getTagCompound().setTag("LoreReplacer", nbt);
//        return hash;
//    }
}
