package ar.edu.unpaz.veterinaria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unpaz.veterinaria.model.BaseEntity;

/**
 * Servicio base que aplica el patron Template Method para operaciones CRUD.
 *
 * Las subclases aportan el repositorio concreto y pueden sobreescribir los
 * metodos gancho {@link #antesDeGuardar(BaseEntity)} y
 * {@link #antesDeEliminar(Long)} para validar reglas de negocio especificas.
 *
 * @param <T> tipo de entidad administrada
 */
public abstract class AbstractCrudService<T extends BaseEntity> {

	protected abstract JpaRepository<T, Long> getRepository();

	/**
	 * Lista todas las entidades del tipo administrado.
	 *
	 * @return entidades persistidas
	 */
	public List<T> listar() {
		return getRepository().findAll();
	}

	/**
	 * Busca una entidad por id.
	 *
	 * @param id identificador tecnico
	 * @return entidad encontrada o vacio si no existe
	 */
	public Optional<T> buscarPorId(Long id) {
		return getRepository().findById(id);
	}

	/**
	 * Guarda la entidad aplicando primero las validaciones de la subclase.
	 *
	 * @param entidad entidad a crear o actualizar
	 * @return entidad persistida
	 */
	public T guardar(T entidad) {
		antesDeGuardar(entidad);
		return getRepository().save(entidad);
	}

	/**
	 * Elimina una entidad por id aplicando primero el gancho de validacion.
	 *
	 * @param id identificador tecnico
	 */
	public void eliminar(Long id) {
		antesDeEliminar(id);
		getRepository().deleteById(id);
	}

	protected void antesDeGuardar(T entidad) {
	}

	protected void antesDeEliminar(Long id) {
	}
}
