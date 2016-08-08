package vn.elca.training.dom;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QDepartment is a Querydsl query type for Department
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QDepartment extends EntityPathBase<Department> {

    private static final long serialVersionUID = -751481089L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDepartment department = new QDepartment("department");

    public final QRoot _super = new QRoot(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QEmployee leader;

    public final StringPath name = createString("name");

    public final ListPath<Project, QProject> projects = this.<Project, QProject>createList("projects", Project.class, QProject.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QDepartment(String variable) {
        this(Department.class, forVariable(variable), INITS);
    }

    public QDepartment(Path<? extends Department> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QDepartment(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QDepartment(PathMetadata<?> metadata, PathInits inits) {
        this(Department.class, metadata, inits);
    }

    public QDepartment(Class<? extends Department> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.leader = inits.isInitialized("leader") ? new QEmployee(forProperty("leader")) : null;
    }

}

