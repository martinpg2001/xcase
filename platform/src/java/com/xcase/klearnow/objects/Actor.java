package com.xcase.klearnow.objects;

public class Actor {
    public String name;
    public String actorType;
    public Address address;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Actor actor = (Actor) obj;
        return actor.name.equals(this.name) && actor.address.equals(this.address);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
