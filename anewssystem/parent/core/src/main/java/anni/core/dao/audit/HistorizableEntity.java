package anni.core.dao.audit;


/**
 * 领域对象自动保存修改历史记录的接口.
 * Hibernate的Event Listener将对实现了此接口的领域对象自动保存修改记录
 * 因为继承于{@link AuditableEntity},因此可轻松获得修改人及修改时间
 *
 * @author calvin
 */
public interface HistorizableEntity extends AuditableEntity {
}
