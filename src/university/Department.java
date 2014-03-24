/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

/**
 *
 * @author Mavrov
 */
public class Department {
    /*
    ALGEBRA,
    ANALYTICAL_MECHANICS,
    COMPLEX_ANALYSIS_AND_TOPOLOGY,
    COMPUTER_INFORMACTICS,
    COMPUTER_SYSTEMS,
    DIFFERENTIAL_EQUASIONS,
    EDUCATION_IN_MATHEMATICS_AND_INFORMATICS,
    GEOMETRY,
    INFORMATION_TECHNOLOGIES,
    MATHEMATICAL_ANALYSIS,
    MATHEMATICAL_LOGIC_AND_APPLICATIONS,
    NUMERICAL_METHODS_AND_ALGORITHMS,
    PROBABILITY_OPERATIONS_REASEARCH_STATISTICS,
    SOFTWARE_TECHNOLOGIES,
    LABORATORY_MATHEMATICAL_MODELING_IN_ECONOMICS;
    */
    
    public Department() {
        name = "";
    }
    
    public Department(String departmentName) {
        name = departmentName;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    private String name;
}
