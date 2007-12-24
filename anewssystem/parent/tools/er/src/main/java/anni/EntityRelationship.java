package anni;

import java.io.*;

import java.sql.*;

import java.text.*;

import java.util.*;

import anni.database.*;

import anni.model.*;

import anni.ui.*;


public class EntityRelationship {
    private Database database;

    public EntityRelationship(Database database) {
        this.database = database;
    }

    // ====================================================
    public void execute() throws Exception {
        System.out.println("model start");

        ModelManager modelManager = ModelManager
            .createModelManager(database);
        System.out.println("model end");

        System.out.println("ui start");

        UIManager uiManager = UIManager.createUIManager(modelManager);
        uiManager.render("er.svg");
        System.out.println("ui end");
    }

    public static void main(String[] args) throws Exception {
        System.out.println("application start");

        if (args.length != 4) {
            System.out.println(
                "[Usage] : java anni.EntityRelationship username password driverName url");
        } else {
            System.out.println("database start");

            Database database = Database.createDatabase(args[0], args[1],
                    args[2], args[3]);
            System.out.println("database end");

            EntityRelationship er = new EntityRelationship(database);
            er.execute();
        }

        System.out.println("application end");
    }
}
