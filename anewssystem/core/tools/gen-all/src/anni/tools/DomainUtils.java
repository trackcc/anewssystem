
package anni.tools;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

public class DomainUtils {
	public static String prefix = "";

    public String createEntity(Class pojo) {
        Annotation[] annotations = pojo.getAnnotations();
        Entity target = null;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == Entity.class) {
                target = (Entity)annotation;
                break;
            }
        }
        StringBuffer buff = new StringBuffer();
        buff.append("@Entity");
        return buff.toString();
    }
    public String createTable(Class pojo) {
        Annotation[] annotations = pojo.getAnnotations();
        Table target = null;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == Table.class) {
                target = (Table)annotation;
                break;
            }
        }
        StringBuffer buff = new StringBuffer();
        buff.append("@Table(name=\"")
            .append(target.name())
            .append("\")");
        return buff.toString();
    }
    public String createField(Field field) {
        StringBuffer buff = new StringBuffer();
        buff.append("private ");

        Class clz = field.getType();
        if (clz.equals(Set.class)) {
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            Type argType = type.getActualTypeArguments()[0];
            Class argClass = (Class) argType;
            buff.append(clz.getSimpleName())
                .append("<").append(argClass.getSimpleName()).append("> ")
                .append(field.getName())
                .append(" = new HashSet<").append(argClass.getSimpleName()).append(">(0);");

        } else {
            buff.append(clz.getSimpleName())
                .append(" ")
                .append(field.getName())
                .append(";");
        }
        return buff.toString();
    }
    public String createGetterAnnotation(Field field, Class clz) throws Exception {
        String getter = "get" + capFirst(field);
        Method method = clz.getDeclaredMethod(getter, new Class[0]);
        StringBuffer buff = new StringBuffer();
        for (Annotation annotation : method.getDeclaredAnnotations()) {
            if (annotation.annotationType() == GenericGenerator.class) {
                buff.append("    @GenericGenerator(name=\"generator\",strategy=\"increment\")\r\n");
            } else if (annotation.annotationType() == Id.class) {
                buff.append("    @Id\r\n");
            } else if (annotation.annotationType() == GeneratedValue.class) {
                buff.append("    @GeneratedValue(generator=\"generator\")\r\n");
            } else if (annotation.annotationType() == Column.class) {
                Column anno = (Column) annotation;
                buff.append("    @Column(name=\"").append(anno.name()).append("\"");
                if (anno.unique()) {
                    buff.append(",unique=true");
                }
                if (!anno.nullable()) {
                    buff.append(",nullable=false");
                }
                if (field.getType() == String.class) {
                    buff.append(",length=").append(anno.length());
                }
                buff.append(")\r\n");
                if (anno.length() > 255) {
                    buff.append("    @Lob\r\n");
                }
            } else if (annotation.annotationType() == ManyToOne.class) {
                buff.append("    @ManyToOne(fetch=FetchType.LAZY)\r\n");
            } else if (annotation.annotationType() == JoinColumn.class) {
                JoinColumn anno = (JoinColumn) annotation;
                buff.append("    @JoinColumn(name=\"").append(anno.name()).append("\")\r\n");
            } else if (annotation.annotationType() == OneToMany.class) {
                OneToMany anno = (OneToMany) annotation;
                buff.append("    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy=\"")
                    .append(anno.mappedBy())
                    .append("\")\r\n");
            } else if (annotation.annotationType() == ManyToMany.class) {
                ManyToMany anno = (ManyToMany) annotation;
                buff.append("    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)\r\n");
                buff.append("    @JoinTable(name=\"").append(makeJoinTable(clz, field))
                    .append("\",joinColumns={@JoinColumn(name=\"")
                    .append(clz.getSimpleName().toUpperCase() + "_ID")
                    .append("\")},inverseJoinColumns={@JoinColumn(name=\"")
                    .append(makeInverseColumn(field))
                    .append("\")})\r\n");
            } else {
                buff.append("    ").append(annotation).append("\r\n");
            }
        }
        if (buff.length() > 2) {
            buff.deleteCharAt(buff.length() - 1);
            buff.deleteCharAt(buff.length() - 1);
        }
        return buff.toString();
    }
    public String createGetter(Field field) {
        StringBuffer buff = new StringBuffer();
        buff.append("    public ").append(paramType(field)).append(" get").append(capFirst(field)).append("() {\r\n")
            .append("        return ").append(field.getName()).append(";\r\n")
            .append("    }");
        return buff.toString();
    }
    public String createSetter(Field field) {
        StringBuffer buff = new StringBuffer();
        buff.append("    public void set").append(capFirst(field)).append("(").append(paramType(field)).append(" ").append(field.getName()).append(") {\r\n")
            .append("        this.").append(field.getName()).append(" = ").append(field.getName()).append(";\r\n")
            .append("    }");
        return buff.toString();
    }

    // ==========================================
    private String paramType(Field field) {
        StringBuffer buff = new StringBuffer();
        Class clz = field.getType();
        if (clz.equals(Set.class)) {
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            Class argClass = (Class) type.getActualTypeArguments()[0];
            buff.append(clz.getSimpleName())
                .append("<").append(argClass.getSimpleName()).append(">");
        } else {
            buff.append(clz.getSimpleName());
        }
        return buff.toString();
    }
    private String capFirst(Field field) {
        String name = field.getName();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    private String makeJoinTable(Class clz, Field field) {
        String name1 = clz.getSimpleName().toUpperCase();
        ParameterizedType type = (ParameterizedType) field.getGenericType();
        Class argClass = (Class) type.getActualTypeArguments()[0];
        String name2 = argClass.getSimpleName().toUpperCase();
        if (name1.compareTo(name2) < 0) {
            return prefix.toUpperCase() + name1 + "_" + name2;
        } else {
            return prefix.toUpperCase() + name2 + "_" + name1;
        }
    }
    private String makeInverseColumn(Field field) {
        ParameterizedType type = (ParameterizedType) field.getGenericType();
        Class argClass = (Class) type.getActualTypeArguments()[0];
        return argClass.getSimpleName().toUpperCase() + "_ID";
    }
}
