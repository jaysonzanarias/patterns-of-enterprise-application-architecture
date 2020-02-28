package ObjectRelationalStructuralPatterns.IdentityField.CompoundKey;

public class Key {
    private Object[] fields;
    public boolean equals(Object obj) {
        if(!(obj instanceof Key)) {
            return false;
        }

        Key otherKey = (Key) obj;

        if(this.fields.length != otherKey.fields.length) {
            return false;
        }

        for(int i=0; i<fields.length; i++) {
            if(!this.fields[i].equals(otherKey.fields[i])) {
                return false;
            }
        }
        return true;
    }

    public Key(Object[] fields) {
        checkKeyNotNull(fields);
        this.fields = fields;
    }

    private void checkKeyNotNull(Object[] fields) {
        if (fields == null) {
            throw new IllegalArgumentException("Cannot have a null key");
        }

        for(int i=0; i<fields.length; i++) {
            if(fields[i] == null) {
                throw new IllegalArgumentException("Cannot have a null element of key");
            }
        }
    }

    public Key(long arg) {
        this.fields = new Object[1];
        this.fields[0] = new Long(arg);
    }

    public Key(Object field) {
        if (field == null) {
            throw new IllegalArgumentException("Cannot have a null key");
        }
        this.fields = new Object[1];
        this.fields[0] = field;
    }

    public Key(Object arg1, Object arg2) {
        this.fields = new Object[2];
        this.fields[0] = arg1;
        this.fields[1] = arg2;
        checkKeyNotNull(fields);
    }

    public Object value(int i) {
        return fields[i];
    }

    public Object value() {
        checkSingleKey();
        return fields[0];
    }

    private void checkSingleKey() {
        if(fields.length > 1) {
            throw new IllegalStateException("Cannot take value on composite key");
        }
    }

    public long longValue() {
        checkSingleKey();
        return longValue(0);
    }

    public long longValue(int i) {
        if(!(fields[i] instanceof Long)) {
            throw new IllegalStateException("Cannot take longValue on non long key");
        }
        return ((Long) fields[i]).longValue();
    }
}
