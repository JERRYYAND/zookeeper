package com.zg.zookeeper.starter.zookeeper.curator;

/**
 * 构建模式
 * @author xyl
 */
public class UserFactory {
	public static void main(String[] args) {
		User user = UserFactory.build().password("123").username("tom").sex("男").school("中南大学");
		System.out.println(user);
	}
	public static User build() {
		return new User();
	}
	
	static class User{
		private String password;
		
		private String username;
		
		private String sex;
		
		private String school;
		
		public User password(String password) {
			this.password=password;
			return this;		
		}

		public User username(String username) {
			this.username=username;
			return this;		
		}

		public User sex(String sex) {
			this.sex=sex;
			return this;		
		}

		public User school(String school) {
			this.school=school;
			return this;		
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getSchool() {
			return school;
		}

		public void setSchool(String school) {
			this.school = school;
		}

		@Override
		public String toString() {
			return "User [password=" + password + ", username=" + username + ", sex=" + sex + ", school=" + school
					+ "]";
		}
		
	}

}
