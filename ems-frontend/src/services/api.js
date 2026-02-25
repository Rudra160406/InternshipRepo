import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

const toError = (error, fallbackMessage) => {
  const payload = error?.response?.data;
  const details = payload?.details || payload;
  const message =
    payload?.message ||
    details?.general ||
    error?.message ||
    fallbackMessage;

  const wrapped = new Error(message);
  wrapped.details =
    details && typeof details === "object" && !Array.isArray(details)
      ? details
      : {};

  return wrapped;
};


export const createEmployee = async (payload) => {
  try {
    const response = await api.post("/employees", payload);
    return response.data;
  } catch (error) {
    throw toError(error, "Unable to create employee.");
  }
};

export const fetchEmployees = async () => {
  try {
    const response = await api.get("/employees");
    return Array.isArray(response.data) ? response.data : [];
  } catch (error) {
    throw toError(error, "Unable to fetch employees.");
  }
};

export const searchEmployees = async (params) => {
  try {
    const response = await api.get("/employees/search", { params });
    return Array.isArray(response.data) ? response.data : [];
  } catch (error) {
    throw toError(error, "Unable to search employees.");
  }
};

export const updateEmployee = async (id, payload) => {
  try {
    const response = await api.put(`/employees/${id}`, payload);
    return response.data;
  } catch (error) {
    throw toError(error, "Unable to update employee.");
  }
};

export const deleteEmployee = async (id) => {
  try {
    await api.delete(`/employees/${id}`);
  } catch (error) {
    throw toError(error, "Unable to delete employee.");
  }
};


export const fetchDepartments = async () => {
  try {
    const response = await api.get("/departments");
    return Array.isArray(response.data) ? response.data : [];
  } catch (error) {
    throw toError(error, "Unable to fetch departments.");
  }
};


export const createHod = async (payload) => {
  try {
    const response = await api.post("/hods", payload);
    return response.data;
  } catch (error) {
    throw toError(error, "Unable to create HOD.");
  }
};

export const fetchHods = async () => {
  try {
    const response = await api.get("/hods");
    return Array.isArray(response.data) ? response.data : [];
  } catch (error) {
    throw toError(error, "Unable to fetch HODs.");
  }
};


export const createProject = async (payload) => {
  try {
    const response = await api.post("/projects", payload);
    return response.data;
  } catch (error) {
    throw toError(error, "Unable to create project.");
  }
};

export const fetchProjectsByEmployee = async (employeeId) => {
  try {
    const response = await api.get(`/projects/employee/${employeeId}`);
    return Array.isArray(response.data) ? response.data : [];
  } catch (error) {
    throw toError(error, "Unable to fetch employee projects.");
  }
};

export const fetchAllProjects = async () => {
  try {
    const response = await api.get("/projects");
    return Array.isArray(response.data) ? response.data : [];
  } catch (error) {
    throw toError(error, "Unable to fetch projects.");
  }
};
