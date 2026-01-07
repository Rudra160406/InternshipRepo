const BASE_URL = "http://localhost:8080/api";

export async function createEmployee(employee) {
  let response;

  try {
    response = await fetch(`${BASE_URL}/employees`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(employee),
    });
  } catch (e) {
    const error = new Error("Cannot reach backend server. Please try later.");
    throw error;
  }

  let data = {};
  try {
    data = await response.json();
    // Ensure data is an object
    if (typeof data !== "object" || data === null) {
      data = {};
    }
  } catch (e) {
    data = {}; 
  }

  if (!response.ok) {
    if (Object.keys(data).length > 0) {
      
    const error = new Error("Validation failed");

    const normalizedErrors = { ...data };

    if (normalizedErrors.city) {
      normalizedErrors["address.city"] = normalizedErrors.city;
      delete normalizedErrors.city;
    }

    error.details = normalizedErrors;
    throw error;
    } else {
      const error = new Error("Something went wrong on server.");
      error.details = {}; 
      throw error;
    }
  }

  return data;
}


export async function fetchEmployees() {
  let response;

  try {
    response = await fetch(`${BASE_URL}/employees`);
  } catch (e) {
    throw new Error("Unable to reach backend for employees.");
  }

  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || "Unable to fetch employees.");
  }

  try {
    const data = await response.json();
    return Array.isArray(data) ? data : [];
  } catch (e) {
    return []; 
  }
}

export async function fetchDepartments() {
  let response;

  try {
    response = await fetch(`${BASE_URL}/departments`);
  } catch (e) {
    throw new Error("Unable to reach backend for departments.");
  }

  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || "Unable to fetch departments.");
  }

  try {
    const data = await response.json();
    return Array.isArray(data) ? data : [];
  } catch (e) {
    return [];
  }
}
