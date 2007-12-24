package anni.ui;

import java.io.*;

import java.util.*;

import anni.model.*;


public class UIManager {
    private Map<String, TableModel> tableModels = new HashMap<String, TableModel>();
    private List<FkModel> fkModels = new ArrayList<FkModel>();
    private TableUI tableUI = new TableUI();
    private FkUI fkUI = new FkUI();

    public void setTableModels(Map<String, TableModel> tableModels) {
        this.tableModels = tableModels;
    }

    public void setFkModels(List<FkModel> fkModels) {
        this.fkModels = fkModels;
    }

    // render
    public void render(String fileName) {
        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(
                        new FileOutputStream(fileName), "utf-8"));

            renderHead(out);
            renderScripts(out);
            renderDefs(out);
            renderBody(out);
            renderFoot(out);

            out.flush();
            out.close();
        } catch (Exception ex) {
        }
    }

    public void renderHead(PrintWriter out) {
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println(
            "<!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.0//EN' 'http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd'>");
        out.println(
            "<svg xmlns:xlink='http://www.w3.org/1999/xlink' xmlns='http://www.w3.org/2000/svg' width='1000' height='700' viewBox='0 0 1000 700'  onmousemove='doDrag(evt)'>");
    }

    public void renderScripts(PrintWriter out) {
        out.println(
            "    <script type='text/ecmascript' xlink:href='er.js'/>");
        out.println("    <script type='text/ecmascript'>");
        out.println("    <![CDATA[");

        for (TableModel tableModel : tableModels.values()) {
            tableUI.renderScripts(tableModel, out);
        }

        out.println();

        for (FkModel fkModel : fkModels) {
            fkUI.renderScripts(fkModel, out);
        }

        out.println("    ]]>");
        out.println("    </script>");
    }

    public void renderDefs(PrintWriter out) {
        out.println("    <defs>");

        for (TableModel tableModel : tableModels.values()) {
            tableUI.renderDefs(tableModel, out);
        }

        for (FkModel fkModel : fkModels) {
            fkUI.renderDefs(fkModel, out);
        }

        out.println("    </defs>");
    }

    public void renderBody(PrintWriter out) {
        for (TableModel tableModel : tableModels.values()) {
            tableUI.renderBody(tableModel, out);
        }

        for (FkModel fkModel : fkModels) {
            fkUI.renderBody(fkModel, out);
        }
    }

    public void renderFoot(PrintWriter out) {
        out.println("</svg>");
    }

    // static
    public static UIManager createUIManager(ModelManager modelManager) {
        UIManager uiManager = new UIManager();
        uiManager.setTableModels(modelManager.getTableModels());
        uiManager.setFkModels(modelManager.getFkModels());

        return uiManager;
    }
}
