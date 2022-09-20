package com.github.soramame0256.lorereplacer;

import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.soramame0256.lorereplacer.LoreReplacer.*;

public class DataUtils {
    private JsonObject jsonObject;
    private final String filePath;
    public DataUtils(String fileName) throws IOException {
        if(Files.notExists(Paths.get(MOD_NAME))) {
            Files.createDirectory(Paths.get(MOD_NAME));
        }
        this.filePath = MOD_NAME + "/" + fileName;
        if(Files.notExists(Paths.get(filePath))){
            FileWriter fw = new FileWriter(filePath);
            PrintWriter writer = new PrintWriter(fw);
            writer.print("{}");
            writer.flush();
            writer.close();
        }
        this.jsonObject = convertFileToJSON(filePath);
    }
    public String getStringData(String index){
        if (this.jsonObject.has(index)) {
            return this.jsonObject.get(index).getAsString();
        }else{
            return "";
        }
    }
    public void saveStringData(String index, String value){
        this.jsonObject.addProperty(index, value);
        try {
            flush();
        }catch(IOException e){
            System.out.println("Saving data failed");
            e.printStackTrace();
        }
    }
    public Number getNumberData(String index){
        if (this.jsonObject.has(index)) {
            return this.jsonObject.get(index).getAsNumber();
        }else{
            return 0;
        }
    }
    public void saveNumberData(String index, Number value){
        this.jsonObject.addProperty(index, value);
        try {
            flush();
        }catch(IOException e){
            System.out.println("Saving data failed");
            e.printStackTrace();
        }
    }
    public Boolean getBooleanData(String index){
        if (this.jsonObject.has(index)) {
            return this.jsonObject.get(index).getAsBoolean();
        }else{
            return false;
        }
    }
    public void saveBooleanData(String index, Boolean value){
        this.jsonObject.addProperty(index, value);
        try {
            flush();
        }catch(IOException e){
            System.out.println("Saving data failed");
            e.printStackTrace();
        }
    }
    public void saveJsonData(String index, JsonElement value){
        this.jsonObject.add(index, value);
        try {
            flush();
        }catch(IOException e){
            System.out.println("Saving data failed");
            e.printStackTrace();
        }
    }
    public JsonObject getRootJson(){
        return this.jsonObject;
    }
    public void flush() throws IOException {
        String print = new GsonBuilder().serializeNulls().setPrettyPrinting().create()
                .toJson(jsonObject);
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(print);
        fileWriter.flush();
        fileWriter.close();
    }
    public void reloadJsonFromFile(){
        this.jsonObject = convertFileToJSON(this.filePath);
    }
    public JsonArray getJsonArrayData(String index){
        if (this.jsonObject.has(index)) {
            return this.jsonObject.get(index).getAsJsonArray();
        }else{
            JsonArray jsonArray = new JsonArray();
            jsonArray.add(new JsonObject());
            return jsonArray;
        }
    }
    //https://gist.github.com/madonnelly/1371597
    public static JsonObject convertFileToJSON (String fileName){
        // Read from File to String
        JsonObject jo = new JsonObject();
        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(fileName));
            jo = jsonElement.getAsJsonObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return jo;
    }
}
