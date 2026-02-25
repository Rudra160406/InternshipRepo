import React from "react";

class ErrorBoundary extends React.Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false };
  }

  static getDerivedStateFromError() {
    return { hasError: true };
  }

  componentDidCatch(error) {
    console.error("UI error captured by ErrorBoundary:", error);
  }

  render() {
    if (this.state.hasError) {
      return (
        <div style={{ padding: "24px", fontFamily: "Segoe UI, sans-serif" }}>
          <h2 style={{ color: "#0f172a" }}>Something went wrong</h2>
          <p style={{ color: "#475569" }}>
            The page encountered an unexpected issue. Please refresh and try
            again.
          </p>
        </div>
      );
    }

    return this.props.children;
  }
}

export default ErrorBoundary;
