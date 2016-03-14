/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.titansinc.stickynotesapp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zubair
 */
public class StickyNotes {

    /**
     * @param args the command line arguments
     */
    protected HashMap<Integer, NotePad> Notes= new HashMap<Integer, NotePad>();
    protected Integer count=0;
    protected Integer ID=0;
    
    StickyNotes() throws IOException{
        if(HasUnfinishedSession()){
           
            try {
               ServerSocket serverSocket = new ServerSocket(2000);
               setCountAndIDValues();
               RestoreWindows();
            } catch (IOException ex) {
               ex.printStackTrace();
               NewSetCountAndIDValues();
               new NotePad(this);
            }
           
        }
        else{
            ServerSocket serverSocket = new ServerSocket(27800);
            new NotePad(this);
        }
        
    }
    
    private void RestoreWindows() {
        try{
        File sessionObj=new File("/var/tmp/.StickyNotes/");
        File[] files=sessionObj.listFiles();
        
        for(int var=0;var<files.length;var++){          
            BufferedReader buffer=new BufferedReader(new InputStreamReader(new FileInputStream(files[var])));
            String Text=buffer.readLine();            
            NotePad note=new NotePad(this, Integer.valueOf(files[var].getName().substring(0,files[var].getName().indexOf("."))), Text);
            
        }
        }catch(Exception e){e.printStackTrace();}
    }
    
    private void setCountAndIDValues(){
            File sessionObj=new File("/var/tmp/.StickyNotes/");
            String filenames[]= sessionObj.list();
            int max=0;
            for(int var=0;var<filenames.length;var++){
                max=Integer.valueOf(filenames[var].substring(0,filenames[var].indexOf(".")));
                if(ID<max){
                    ID=max;
                }
            }
            ID=ID++;
           
            count=filenames.length;
    }
    
    private void NewSetCountAndIDValues(){
         File sessionObj=new File("/var/tmp/.StickyNotes/");
            String filenames[]= sessionObj.list();
            int max=0;
            for(int var=0;var<filenames.length;var++){
                max=Integer.valueOf(filenames[var].substring(0,filenames[var].indexOf(".")));
                if(ID<max){
                    ID=max;
                }
            }
            ID=ID+new Random().nextInt(max+1000000);
            count=0;
        
    }
    
    private boolean HasUnfinishedSession(){
        File sessionObj=new File("/var/tmp/.StickyNotes/");
        String[] list=sessionObj.list();
         
        if(list.length==0){
            return false;
        }
        else{
            return true;
        }

    }
    
    
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        new StickyNotes();
    }
    
}
