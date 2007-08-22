package anni.core.security.service;

import java.sql.*;

import java.util.*;

import javax.sql.DataSource;

import anni.core.security.resource.*;

import junit.framework.TestCase;

import org.acegisecurity.providers.dao.UserCache;

import org.acegisecurity.userdetails.User;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import static org.easymock.EasyMock.*;

import org.easymock.classextension.EasyMock;

import org.springframework.jdbc.datasource.DriverManagerDataSource;


public class DaoAuthenticationServiceTest extends TestCase {
    DaoAuthenticationService service = null;
    String loadusersQuery = "select name,passwd,status from ss_users";
    String authoritiesByUsernameQuery = "select r.name from ss_users u,ss_roles r,ss_user_role ur where u.id = ur.user_id and r.id = ur.role_id and u.name = ?";
    String loadResourcesQuery = "select res_string, res_type from ss_resources";
    String authoritiesByResourceQuery = "select r.name from ss_resources c,ss_roles r,ss_role_resc rc where c.id = rc.resc_id and r.id = rc.role_id and c.res_string = ?";

    @Override
    protected void setUp() throws Exception {
        DataSource dataSource = new DriverManagerDataSource("org.hsqldb.jdbcDriver",
                "jdbc:hsqldb:res:/hsqldb/test", "sa", "");

        service = new DaoAuthenticationService();
        service.setLoadUsersQuery(loadusersQuery);
        service.setAuthoritiesByUsernameQuery(authoritiesByUsernameQuery);
        service.setLoadResourcesQuery(loadResourcesQuery);
        service.setAuthoritiesByResourceQuery(authoritiesByResourceQuery);
        service.setRolePrefix("");
        service.setDataSource(dataSource);
    }

    @Override
    protected void tearDown() {
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testGetResources() throws Exception {
        List<Resource> resources = service.getResources();
    }

    public void testGetUsers() throws Exception {
        List<User> users = service.getUsers();
    }
}
