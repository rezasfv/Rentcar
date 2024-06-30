package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Basic interface for all DAO classes
 *
 * @version 1.0
 * @since 1.0
 *
 * @param <T> the type of the output parameter
 */
public abstract class BasicDAO<T> implements DataAccessObject<T> {

	/**
	 * The connection to the database
	 */
	protected final Connection c;
	/**
	 * The output parameter
	 */
	protected T outputParam = null;
	/**
	 * A LOGGER available for all the subclasses.
	 */
	protected static final Logger LOGGER = LogManager.getLogger(BasicDAO.class, StringFormatterMessageFactory.INSTANCE);
	/**
	 * The lock to synchronize the access to the DAO
	 */
	private final Object lock = new Object();
	/**
	 * A flag to check if the DAO has been accessed
	 */
	private boolean accessed = false;

	/**
	 * Constructor for a basic DAO
	 * @param c the connection to the database
	 * @throws NullPointerException if the connection is null
	 * @throws SQLException if the connection is not valid
	 */
	protected BasicDAO(final Connection c) throws NullPointerException, SQLException {
		if (c == null){
			LOGGER.error("The connection cannot be null.");
			throw new NullPointerException("The connection cannot be null.");
		}
		this.c = c;

		try {
			// ensure that autocommit is true
			c.setAutoCommit(true);
			LOGGER.debug("Auto-commit set to default value true.");
		} catch (final SQLException e) {
			LOGGER.warn("Unable to set connection auto-commit to true.", e);
		}
	}

	/**
	 * Access the database
	 *
	 * @return the DataAccessObject
	 * @throws SQLException if an error occurs during the database access
	 */
	public final DataAccessObject<T> access() throws SQLException {
		synchronized (lock) {
			try {
				if (accessed) {
					LOGGER.error("Cannot use a DataAccessObject more than once.");
					throw new SQLException("Cannot use a DataAccessObject more than once.");
				}
			} finally {
				accessed = true;
			}
		}
		try {
			doAccess();

			try {
				c.close();
				LOGGER.debug("Connection successfully closed.");
			} catch (final SQLException e) {
				LOGGER.error("Unable to close the connection to the database.", e);
				throw e;
			}
		} catch (final Throwable t) {

			LOGGER.error("Unable to perform the requested database access operation.", t);

			try {
				if (!c.getAutoCommit()) {
					// autoCommit == false => transaction needs to be rolled back
					c.rollback();
					LOGGER.info("Transaction successfully rolled-back.");
				}
			} catch (final SQLException e) {
				LOGGER.error("Unable to roll-back the transaction.", e);
			} finally {
				try {
					c.close();
					LOGGER.debug("Connection successfully closed.");
				} catch (final SQLException e) {
					LOGGER.error("Unable to close the connection to the database.", e);
				}
			}

			if (t instanceof SQLException) {
				throw (SQLException) t;
			} else {
				throw new SQLException("Unable to perform the requested database access operation.", t);
			}
		}

		return this;
	}

	@Override
	/**
	 * Get the output parameter
	 *
	 * @return the output parameter
	 */
	public final T getOutputParam(){
		synchronized (lock) {
			if (!accessed) {
				LOGGER.error("Cannot retrieve the output parameter before accessing the database.");
				throw new IllegalStateException("Cannot retrieve the output parameter before accessing the database.");
			}
		}
		return outputParam;
	}

	/**
	 * Access the database
	 *
	 * @throws SQLException if an error occurs during the database access
	 * @throws Exception if an error occurs during the database access
	 */
	protected abstract void doAccess() throws SQLException, Exception;
}
