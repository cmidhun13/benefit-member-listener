# benefit-member-listener


Member listener: http://18.223.195.84:8025

## API List
| API	| Type	| Url					| Controller|
| ----	| ---	| --					| ----------|
| ActivationCodeAPI	| POST	| /core/members/v2/activate					| MemberController|
| Update Profile	| POST	| /core/members/v2/createOrUpdate			| MemberController|
| Cancel Enrollment	| POST	| /core/memberships/v2/deactivate			| MembershipController|
| Step 2: enroll	| POST	| /core/members/v2/enroll					| MemberController|
| onboardEmail		| POST	| /core/members/v2/onboard-email			| MemberController|
| onboardXLS CSV	| POST	| /core/members/v2/member/onboard-xls		| MemberController|
| File Upload		| POST	| /core/members/v2/member/file-upload		| MemberController|
| decryption		| GET	| /core/members/v2/decryption?memberid=565	| MemberController|
| encryption		| GET	| /core/members/v2/encryption?memberid=565	| MemberController|
| suspend			| POST	| /core/members/v2/member/status			| MemberController|


## Steps to Run an application
1. Take the latest copy of the code from the repository.
2. Open project.
3. build the project using Gradle->Project->Tasks->build->build.
4. Once Project get build successfully, press Shift+F10 to run it.
