package com.google.protobuf;

class GeneratedMessageInfoFactory implements MessageInfoFactory {
    private static final GeneratedMessageInfoFactory instance = new GeneratedMessageInfoFactory();

    private GeneratedMessageInfoFactory() {
    }

    public static GeneratedMessageInfoFactory getInstance() {
        return instance;
    }

    public boolean isSupported(Class<?> messageType) {
        return GeneratedMessageLite.class.isAssignableFrom(messageType);
    }

    public MessageInfo messageInfoFor(Class<?> messageType) {
        if (!GeneratedMessageLite.class.isAssignableFrom(messageType)) {
            String valueOf = String.valueOf(messageType.getName());
            throw new IllegalArgumentException(valueOf.length() != 0 ? "Unsupported message type: ".concat(valueOf) : new String("Unsupported message type: "));
        }
        try {
            return (MessageInfo) GeneratedMessageLite.getDefaultInstance(messageType.asSubclass(GeneratedMessageLite.class)).buildMessageInfo();
        } catch (Exception e) {
            String valueOf2 = String.valueOf(messageType.getName());
            throw new RuntimeException(valueOf2.length() != 0 ? "Unable to get message info for ".concat(valueOf2) : new String("Unable to get message info for "), e);
        }
    }
}
