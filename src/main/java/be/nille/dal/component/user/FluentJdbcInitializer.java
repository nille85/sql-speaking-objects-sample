/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.dal.component.user;
import javax.sql.DataSource;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.codejargon.fluentjdbc.api.query.Query;

/**
 *
 * @author Niels Holvoet
 */
public class FluentJdbcInitializer implements RepositoryInitializer<Query>{
    
    private final DataSource dataSource;
    
    public FluentJdbcInitializer(final DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Query initialize() {
        FluentJdbc fluentJdbc = new FluentJdbcBuilder()
                .connectionProvider(dataSource)
                .build();
        return fluentJdbc.query();
    }
    
    
   
    
}
