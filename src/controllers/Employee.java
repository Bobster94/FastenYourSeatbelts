package controllers;

/**
 * This class will store information about the employee who is currently logged in
 * @author Bas
 * @version 1.0
 */
public class Employee {
    
    private String username;
    private int employeeID;
    private int functionID;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getFunctionID() {
        return functionID;
    }

    public void setFunctionID(int functionID) {
        this.functionID = functionID;
    }
}
