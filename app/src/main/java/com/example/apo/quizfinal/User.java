package com.example.apo.quizfinal;

/**
 * Created by apo on 10/12/2016.
 */

import java.io.Serializable;

@SuppressWarnings("serial") //With this annotation we are going to hide compiler warnings
public class User implements Serializable {

        //private variables
        int _id;
        String _name;
        String _password;

        // Empty constructor
        public User(){

        }
        // constructor
        public User(int id, String name, String _password){
            this._id = id;
            this._name = name;
            this._password = _password;
        }

        // constructor
        public User(String name, String _password){
            this._name = name;
            this._password = _password;
        }
        // getting ID
        public int getID(){
            return this._id;
        }

        // setting id
        public void setID(int id){
            this._id = id;
        }

        // getting name
        public String getName(){
            return this._name;
        }

        // setting name
        public void setName(String name){
            this._name = name;
        }

        // getting phone number
        public String getPassword(){
            return this._password;
        }

        // setting phone number
        public void setPassword(String password){
            this._password = password;
        }
}


