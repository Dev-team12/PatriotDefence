package defencer.temp;


/**
 * @author Nikita on 17.04.2017.
 */
public class HibernateQueryBuilder {

    public static final String ID_FIELD = "id";


    public static final int SELECT_QUERY = 1;

    public static final int INSERT_QUERY = 2;

    public static final int UPDATE_QUERY = 3;

    public static final int DELETE_QUERY = 4;

    private String query = "";

    private int currentQueryType;


    public HibernateQueryBuilder(int queryType, Class inputClass) {

        this.currentQueryType = queryType;

        String className = inputClass.getName();

        switch (queryType) {

            case SELECT_QUERY:
                query = "from " + className + " where ";
                break;

            case INSERT_QUERY:
                query = "insert into " + className + "(";
                break;

            case UPDATE_QUERY:
                query = "update " + className + " set ";
                break;

            case DELETE_QUERY:
                query = "delete " + className + " where ";
                break;

            default:
                throw new IllegalArgumentException();
        }
    }


    /**
     * Add field with value.
     */
    public HibernateQueryBuilder with(String field, Object value) {

        System.out.println(currentQueryType);

        switch (currentQueryType) {

            case INSERT_QUERY:

                if (query.substring(query.indexOf(query.length() - 1)).equals("(")) {
                    query += "\'" + value + "\'";
                } else {
                    query += ",\'" + value + "\'";
                }
                break;

            case SELECT_QUERY:
                query += field + "= \'" + value + "\'";
                break;

            case UPDATE_QUERY:
                query += field + "= \'" + value + "\'";
                break;

            case DELETE_QUERY:
                query += field + "= \'" + value + "\'";
                break;

            default:
                throw new IllegalArgumentException();
        }

        return this;
    }


    /**
     * Get string of query.
     */
    public String getQuery() {

        switch (currentQueryType) {
            case INSERT_QUERY:
                query += ")";
                break;

            default:
                break;
        }

        return query;
    }
}
