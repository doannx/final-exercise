package vn.elca.training.dom;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QEmployee is a Querydsl query type for Employee
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QEmployee extends EntityPathBase<Employee> {

    private static final long serialVersionUID = 2139290011L;

    public static final QEmployee employee = new QEmployee("employee");

    public final QRoot _super = new QRoot(this);

    public final DateTimePath<java.util.Date> birthDate = createDateTime("birthDate", java.util.Date.class);

    public final StringPath firstName = createString("firstName");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath lastName = createString("lastName");

    public final ListPath<Project, QProject> projects = this.<Project, QProject>createList("projects", Project.class, QProject.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final StringPath visa = createString("visa");

    public QEmployee(String variable) {
        super(Employee.class, forVariable(variable));
    }

    public QEmployee(Path<? extends Employee> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmployee(PathMetadata<?> metadata) {
        super(Employee.class, metadata);
    }

}

