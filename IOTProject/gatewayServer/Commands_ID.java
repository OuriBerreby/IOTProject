package gatewayServer;

public enum Commands_ID {
	REGISTER_COMPANY("R_C"),
    REGISTER_USER("R_U"),
    REGISTER_PRODUCT("R_P"),
    UPDATE_IOT("U_I");

    private final String id;

    Commands_ID(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static Commands_ID fromId(String id) {
        for (Commands_ID command : Commands_ID.values()) {
            if (command.getId().equals(id)) {
                return command;
            }
        }
        throw new IllegalArgumentException("Invalid command id: " + id);
    }

}
