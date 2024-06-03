package com.nationalbank.model;

public class JwtResponse {

	private String jwtToken;
	
	private String username;
	
	private String status;
	
	private Long accountId;
	
	private String accountNumber;

	public JwtResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtResponse(Builder builder) {
		super();
		this.jwtToken =builder.jwtToken;
		this.username = builder.username;
		this.status = builder.status;
		this.accountId= builder.accountId;
		this.accountNumber=builder.accountNumber;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static Builder builder() {
        return new Builder();
    }
	
    // Builder class
    public static class Builder {
        private String jwtToken;
        private String username;
        private String status;
        private Long accountId; 
        private String accountNumber;

        public Builder() {}

        public Builder jwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }
        
        public Builder status(String status) {
            this.status = status;
            return this;
        }
        
        public Builder accountId(Long accountId) {
            this.accountId = accountId;
            return this;
        }
        public Builder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public JwtResponse build() {
            return new JwtResponse(this);
        }
    }

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public String toString() {
		return "JwtResponse [jwtToken=" + jwtToken + ", username=" + username + ", status=" + status + ", accountId="
				+ accountId + ", accountNumber=" + accountNumber + "]";
	}
}
