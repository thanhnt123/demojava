package com.demoproject.model;

import com.demoproject.framework.db.ConnectionIF;
import com.demoproject.framework.db.ConnectionManager;
import com.demoproject.helper.ConfigInfo;
import com.demoproject.thrift.account.TAccount;
import com.demoproject.thrift.account.TAccountListResult;
import com.demoproject.thrift.account.TAccountResult;
import com.demoproject.thrift.tcommon.EStatusResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class AccountDA {

    private static final Logger logger = Logger.getLogger(AccountDA.class);

    private static final AccountDA instance = new AccountDA();

    private static final String SQL_INSERT = "INSERT INTO %s(`username`, `password`, `displayname`,"
            + "`status`, `verifiedAt`, `created_at`, `updated_at`)"
            + "VALUES(?,?,?,?,NOW(),NOW(),NOW())";

    private static final String SQL_UPDATE = "UPDATE %s SET `displayname`=?, `updated_at`=NOW()"
            + " WHERE `username` = ?";

    private static final String GET_BY_ID_QUERY = "SELECT * FROM %s WHERE `username` = ?";

    public static AccountDA getInstance() {
        return instance;
    }

    private static TAccount createFromReader(ResultSet rs) throws Exception {
        TAccount item = new TAccount();
        item.setId(rs.getInt("id"));
        item.setUsername(rs.getString("username"));
        item.setPassword(rs.getString("password"));
        item.setDisplayname(rs.getString("displayname"));
        item.setStatus(rs.getString("status"));
        item.setVerifiedAt(rs.getInt("verifiedAt"));
        item.setCreatedAt(rs.getString("created_at"));
        item.setUpdatedAt(rs.getString("updated_at"));
        return item;
    }

    public TAccountResult getPlayerByUsername(String uid) {
        TAccountResult result = new TAccountResult();
        TAccount player = null;
        ConnectionIF cm = ConnectionManager.getInstance(ConfigInfo.DEMO_DB);
        Connection cnn = cm.borrow();
        String query = String.format(GET_BY_ID_QUERY, ConfigInfo.TABLE_ACCOUNT);
        try (PreparedStatement stmt = cnn.prepareStatement(query)) {
            stmt.setString(1, uid);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                player = createFromReader(rs);
            }
            result.setValue(player);
            result.setStatus(EStatusResult.OK);
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            cm.giveBack(cnn);
        }
        return result;
    }

    public boolean insert(TAccount value) {
        boolean result = false;
        ConnectionIF cm = ConnectionManager.getInstance(ConfigInfo.DEMO_DB);
        Connection cnn = cm.borrow();
        String query = String.format(SQL_INSERT, ConfigInfo.TABLE_ACCOUNT);
        try (PreparedStatement stmt = cnn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, value.getUsername());
            stmt.setString(2, value.getPassword());
            stmt.setString(3, value.getDisplayname());
            stmt.setString(4, value.getStatus());
            
            result = stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            cm.giveBack(cnn);
        }
        return result;
    }

    public boolean update(TAccount value) {
        boolean result = false;
        ConnectionIF cm = ConnectionManager.getInstance(ConfigInfo.DEMO_DB);
        Connection cnn = cm.borrow();

        String query = String.format(SQL_UPDATE, ConfigInfo.TABLE_ACCOUNT);
        try (PreparedStatement stmt = cnn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, value.getDisplayname());
            stmt.setString(2, value.getUsername());

            result = stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            logger.error("Exception:" + ex);
        } finally {
            cm.giveBack(cnn);
        }
        return result;
    }

    public boolean remove(String username) {
        boolean result = false;
        ConnectionIF cm = ConnectionManager.getInstance(ConfigInfo.DEMO_DB);
        Connection cnn = cm.borrow();

        String query = "DELETE from " + ConfigInfo.TABLE_ACCOUNT + " WHERE username='" + username + "'";
        try (PreparedStatement stmt = cnn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            result = stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            cm.giveBack(cnn);
        }
        return result;
    }

    public boolean updateLastLogin(String username, long loginDate) {
        boolean result = false;
        ConnectionIF cm = ConnectionManager.getInstance(ConfigInfo.DEMO_DB);
        Connection cnn = cm.borrow();

        String query = "UPDATE " + ConfigInfo.TABLE_ACCOUNT + " SET lastedLogin=" + loginDate + " WHERE username='" + username + "'";

        try (PreparedStatement stmt = cnn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            result = stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            cm.giveBack(cnn);
        }
        return result;
    }

    public List<TAccount> getListAll(String whereClauset) {
        ConnectionIF cm = ConnectionManager.getInstance(ConfigInfo.DEMO_DB);
        Connection cnn = cm.borrow();
        String QUERY_SQL = " SELECT * "
                + " FROM " + ConfigInfo.TABLE_ACCOUNT + " WHERE 1 " + whereClauset + " ";

        List<TAccount> lstItem = new ArrayList<>();
        try (PreparedStatement stmt = cnn.prepareStatement(QUERY_SQL)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TAccount item = createFromReader(rs);
                if (item != null) {
                    lstItem.add(item);
                }
            }
            return lstItem;
        } catch (SQLException ex) {
            logger.error(ex);
        } catch (Exception e) {
            logger.error(e);
        } finally {
            cm.giveBack(cnn);
        }
        return null;
    }

    public TAccountListResult getListPaging(String whereClause, int offset, int count) {
        TAccountListResult result = new TAccountListResult();
        ConnectionIF cm = ConnectionManager.getInstance(ConfigInfo.DEMO_DB);
        Connection cnn = cm.borrow();
        String QUERY_SQL = " SELECT * "
                + " FROM " + ConfigInfo.TABLE_ACCOUNT + " WHERE 1 " + whereClause + " LIMIT " + offset + "," + count + "; ";

        List<TAccount> lstItem = new ArrayList<>();
        try (PreparedStatement stmt = cnn.prepareStatement(QUERY_SQL)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TAccount item = createFromReader(rs);
                if (item != null) {
                    lstItem.add(item);
                }
            }
            rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            long totalRecord = 0;
            if (rs.next()) {
                totalRecord = rs.getLong(1);
            }
            //set data
            result.setStatus(EStatusResult.OK);
            result.setListData(lstItem);
            result.setTotalRecords(totalRecord);
        } catch (SQLException ex) {
            logger.error(ex);
        } catch (Exception e) {
            logger.error(e);
            cm.giveBack(cnn);
        } finally {
            cm.giveBack(cnn);
        }
        return result;
    }

}
