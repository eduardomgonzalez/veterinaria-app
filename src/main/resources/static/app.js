// Rutas REST expuestas por los controllers de Spring Boot.
const endpoints = {
	duenios: "/api/duenios",
	mascotas: "/api/mascotas",
	turnos: "/api/turnos"
};

// Estado local del frontend: guarda la ultima informacion recibida desde la API.
const state = {
	duenios: [],
	mascotas: [],
	turnos: []
};

const currency = new Intl.NumberFormat("es-AR", {
	style: "currency",
	currency: "ARS"
});

/**
 * Hace una solicitud HTTP al backend y devuelve la respuesta como JSON.
 *
 * Si no se pasan opciones, fetch usa GET por defecto.
 * Si se pasan opciones, se puede indicar POST, PUT o DELETE.
 */
async function fetchJson(url, options) {
	// fetch() usa GET por defecto si no le indicás otro método.
	const response = await fetch(url, options);

	if (!response.ok) {
		throw new Error("No se pudo completar la operacion");
	}

	if (response.status === 204) {
		return null;
	}

	return response.json();
}

// Muestra una fila de texto cuando una tabla no tiene registros.
function renderEmpty(tbody, columns, message) {
	tbody.innerHTML = `<tr><td class="empty" colspan="${columns}">${message}</td></tr>`;
}

// Limpia un formulario y borra el id oculto para volver al modo "crear".
function resetForm(formId) {
	const form = document.getElementById(formId);
	form.reset();
	form.elements.id.value = "";
}

// Crea las opciones HTML de un select usando una lista recibida desde el backend.
function optionList(items, getLabel) {
	return items.map(item => `<option value="${item.id}">${getLabel(item)}</option>`).join("");
}

/**
 * Renderiza la tabla de duenios y actualiza el select de duenios usado por mascotas.
 */
function renderDuenios() {
	const tbody = document.getElementById("duenios-tabla");
	document.getElementById("total-duenios").textContent = state.duenios.length;

	document.getElementById("mascota-duenio").innerHTML = optionList(state.duenios, duenio => duenio.nombre);

	if (state.duenios.length === 0) {
		renderEmpty(tbody, 4, "No hay dueños cargados.");
		return;
	}

	tbody.innerHTML = state.duenios.map(duenio => `
		<tr>
			<td>${duenio.nombre}</td>
			<td>${duenio.dni ?? ""}</td>
			<td>${duenio.telefono ?? ""}</td>
			<td class="row-actions">
				<button data-action="edit" data-type="duenio" data-id="${duenio.id}">Editar</button>
				<button data-action="delete" data-type="duenio" data-id="${duenio.id}">Borrar</button>
			</td>
		</tr>
	`).join("");
}

/**
 * Renderiza la tabla de mascotas y actualiza el select de mascotas usado por turnos.
 */
function renderMascotas() {
	const tbody = document.getElementById("mascotas-tabla");
	document.getElementById("total-mascotas").textContent = state.mascotas.length;

	document.getElementById("turno-mascota").innerHTML = optionList(state.mascotas, mascota => `${mascota.nombre} (${mascota.especie})`);

	if (state.mascotas.length === 0) {
		renderEmpty(tbody, 4, "No hay mascotas cargadas.");
		return;
	}

	tbody.innerHTML = state.mascotas.map(mascota => `
		<tr>
			<td>${mascota.nombre}</td>
			<td><span class="tag">${mascota.especie}</span></td>
			<td>${mascota.duenio?.nombre ?? "Sin dueño"}</td>
			<td class="row-actions">
				<button data-action="edit" data-type="mascota" data-id="${mascota.id}">Editar</button>
				<button data-action="delete" data-type="mascota" data-id="${mascota.id}">Borrar</button>
			</td>
		</tr>
	`).join("");
}

/**
 * Renderiza la tabla de turnos con los datos que devuelve /api/turnos.
 */
function renderTurnos() {
	const tbody = document.getElementById("turnos-tabla");
	document.getElementById("total-turnos").textContent = state.turnos.length;

	if (state.turnos.length === 0) {
		renderEmpty(tbody, 6, "No hay turnos cargados.");
		return;
	}

	tbody.innerHTML = state.turnos.map(turno => `
		<tr>
			<td>${formatDateTime(turno.fechaHora)}</td>
			<td>${turno.mascota?.nombre ?? "Sin mascota"}</td>
			<td>${turno.motivo}</td>
			<td><span class="tag">${turno.estado}</span></td>
			<td>${currency.format(turno.costoEstimado)}</td>
			<td class="row-actions">
				<button data-action="edit" data-type="turno" data-id="${turno.id}">Editar</button>
				<button data-action="delete" data-type="turno" data-id="${turno.id}">Borrar</button>
			</td>
		</tr>
	`).join("");
}

// Adapta la fecha ISO que devuelve Spring para mostrarla mas legible en la tabla.
function formatDateTime(value) {
	if (!value) {
		return "";
	}
	return value.replace("T", " ").slice(0, 16);
}

/**
 * Carga los datos principales de la pantalla.
 *
 * Hace tres GET en paralelo:
 * - GET /api/duenios
 * - GET /api/mascotas
 * - GET /api/turnos
 *
 * Luego guarda las respuestas en state y vuelve a dibujar tablas y selects.
 */
async function cargarDatos() {
	const [duenios, mascotas, turnos] = await Promise.all([
		fetchJson(endpoints.duenios),
		fetchJson(endpoints.mascotas),
		fetchJson(endpoints.turnos)
	]);

	state.duenios = duenios;
	state.mascotas = mascotas;
	state.turnos = turnos;

	renderDuenios();
	renderMascotas();
	renderTurnos();
}

// Copia los datos de un duenio seleccionado al formulario para editarlo.
function fillDuenioForm(duenio) {
	const form = document.getElementById("duenio-form");
	form.elements.id.value = duenio.id;
	form.elements.nombre.value = duenio.nombre ?? "";
	form.elements.dni.value = duenio.dni ?? "";
	form.elements.telefono.value = duenio.telefono ?? "";
	form.elements.email.value = duenio.email ?? "";
	form.elements.direccion.value = duenio.direccion ?? "";
}

// Copia los datos de una mascota seleccionada al formulario para editarla.
function fillMascotaForm(mascota) {
	const form = document.getElementById("mascota-form");
	form.elements.id.value = mascota.id;
	form.elements.nombre.value = mascota.nombre ?? "";
	form.elements.fechaNacimiento.value = mascota.fechaNacimiento ?? "";
	form.elements.especie.value = mascota.especie ?? "PERRO";
	form.elements.raza.value = mascota.raza ?? "";
	form.elements.peso.value = mascota.peso ?? 0;
	form.elements.duenioId.value = mascota.duenio?.id ?? "";
}

// Copia los datos de un turno seleccionado al formulario para editarlo.
function fillTurnoForm(turno) {
	const form = document.getElementById("turno-form");
	form.elements.id.value = turno.id;
	form.elements.fechaHora.value = turno.fechaHora?.slice(0, 16) ?? "";
	form.elements.motivo.value = turno.motivo ?? "CONTROL";
	form.elements.estado.value = turno.estado ?? "PENDIENTE";
	form.elements.mascotaId.value = turno.mascota?.id ?? "";
	form.elements.observacion.value = turno.observacion ?? "";
}

// Alta o modificacion de duenios. Si hay id usa PUT; si no hay id usa POST.
document.getElementById("duenio-form").addEventListener("submit", async event => {
	event.preventDefault();
	const form = event.currentTarget;
	const data = new FormData(form);
	const id = data.get("id");

	await fetchJson(id ? `${endpoints.duenios}/${id}` : endpoints.duenios, {
		method: id ? "PUT" : "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({
			nombre: data.get("nombre"),
			dni: data.get("dni"),
			telefono: data.get("telefono"),
			email: data.get("email"),
			direccion: data.get("direccion")
		})
	});

	resetForm("duenio-form");
	await cargarDatos();
});

// Alta o modificacion de mascotas. Envia duenioId porque el backend debe asociar la mascota a un duenio real.
document.getElementById("mascota-form").addEventListener("submit", async event => {
	event.preventDefault();
	const form = event.currentTarget;
	const data = new FormData(form);
	const id = data.get("id");

	await fetchJson(id ? `${endpoints.mascotas}/${id}` : endpoints.mascotas, {
		method: id ? "PUT" : "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({
			nombre: data.get("nombre"),
			fechaNacimiento: data.get("fechaNacimiento") || null,
			especie: data.get("especie"),
			raza: data.get("raza"),
			peso: Number(data.get("peso")),
			duenioId: Number(data.get("duenioId"))
		})
	});

	resetForm("mascota-form");
	await cargarDatos();
});

// Alta o modificacion de turnos. Envia mascotaId y el backend calcula el costo segun el motivo.
document.getElementById("turno-form").addEventListener("submit", async event => {
	event.preventDefault();
	const form = event.currentTarget;
	const data = new FormData(form);
	const id = data.get("id");

	await fetchJson(id ? `${endpoints.turnos}/${id}` : endpoints.turnos, {
		method: id ? "PUT" : "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({
			fechaHora: data.get("fechaHora"),
			motivo: data.get("motivo"),
			estado: data.get("estado"),
			mascotaId: Number(data.get("mascotaId")),
			observacion: data.get("observacion")
		})
	});

	resetForm("turno-form");
	await cargarDatos();
});

// Maneja los botones de limpiar, editar y borrar desde un unico listener.
document.body.addEventListener("click", async event => {
	const button = event.target.closest("button");

	if (!button) {
		return;
	}

	if (button.dataset.reset) {
		resetForm(button.dataset.reset);
		return;
	}

	const { action, type, id } = button.dataset;

	if (action === "edit" && type === "duenio") {
		fillDuenioForm(state.duenios.find(item => item.id === Number(id)));
	}
	if (action === "edit" && type === "mascota") {
		fillMascotaForm(state.mascotas.find(item => item.id === Number(id)));
	}
	if (action === "edit" && type === "turno") {
		fillTurnoForm(state.turnos.find(item => item.id === Number(id)));
	}

	if (action === "delete") {
		const endpoint = endpoints[`${type}s`];
		try {
			await fetchJson(`${endpoint}/${id}`, { method: "DELETE" });
			await cargarDatos();
		} catch (error) {
			console.error(error);
			alert("No se pudo eliminar. Primero elimina los datos asociados.");
		}
	}
});

// Primera carga de la pantalla: al cargarse app.js, se consulta la API y se dibuja la vista.
cargarDatos().catch(error => {
	console.error(error);
	alert(error.message);
});
