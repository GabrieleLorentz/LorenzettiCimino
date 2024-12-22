abstract sig User{}

sig Student extends User{
	ApplyTo: Internship,
	RecommendedTo: Internship
}

sig Company extends User{
	Offer: set Internship
}

sig Internship{
	RecommendedStudents: set Student,
	AppliedStudents: set Student,
	AllowedStudents: set Student,
	RejBefForm: set Student,
	SelectedStudents: set Student,
	RejAfterForm: set Student,
	OwnedBy: one Company,
	ToCompile: one InterviewForm,
}

sig InterviewForm{
	Compile: one Form,
	CompiledBy: set Student
}

sig Form{}


//An interviewForm must be unique and associated to exactly one internship.
fact UniqueInterviewForm {
	all iF: InterviewForm | one iF.~ToCompile
	all f: Form | one f.~Compile
}

//A student that apply to an internship must be binded with it
fact Bivalent_application_1 {
	all s: Student | s.ApplyTo in s.~AppliedStudents
	all i: Internship | i.AppliedStudents in i.~ApplyTo
}

//A student if recommended to an internship must be binded with it
fact Bivalent_recommendation_1 {
	all s: Student | s.RecommendedTo in s.~RecommendedStudents
	all i: Internship | i.RecommendedStudents in i.~RecommendedTo
}

//One internship must be owned by only one company, that is the one who offer it
fact UniqueOwned {
	all i: Internship | one i.~Offer
}
fact OwnershipConsistency {
	all c: Company, i: Internship | 
		i in c.Offer implies i.OwnedBy = c
}

//A student can not be both
fact noSelnorRec{
	all s: Student |all i: Internship |
		s !in (i.RecommendedStudents & i.AppliedStudents)
		and s !in (i.AllowedStudents & i.RejBefForm)
		and s !in (i.SelectedStudents & i.RejAfterForm)
		and s !in (i.SelectedStudents & i.RejBefForm)
		and s !in (i.RejBefForm & i.RejAfterForm)
}

//A student which is not applied or recommended to an internship can not be
//allowed or rejected by that internship
fact not_jumping_phases{
	all s: Student |all i: Internship |
		(s !in (i.RecommendedStudents + i.AppliedStudents)) implies
		s !in (i.AllowedStudents + i.RejBefForm)
}

//A student which is applied or recommended to an internship must be allowed
//to start the selection process
fact Allow_Process{
	all s: Student |all i: Internship |
		(s in i.RecommendedStudents or s in i.AppliedStudents)
		implies ((some i.AllowedStudents or some i.RejBefForm) and
			s in i.AllowedStudents or s in i.RejBefForm)
}

//An allowed student must be selected for the internship or rejected
fact Sel_Process{
	all s: Student |all i: Internship |
		s in i.AllowedStudents
		implies ((some i.SelectedStudents or some i.RejAfterForm)
			and s in i.SelectedStudents or s in i.RejAfterForm)
}

//Every student that has been selected or rejected after the form must have
//compiled the form
fact Obliged_To_compile{
	all s: Student |all i: Internship |
		s in i.(SelectedStudents + RejAfterForm)
		implies s in i.ToCompile.CompiledBy
}


pred show {
	one s, s2: Student | one i, i2: Internship |
		s in i.RecommendedStudents and
		s in i.SelectedStudents and
		s !in i2.AppliedStudents and s !in i2.RecommendedStudents and
		s2 in i2.AppliedStudents and s2 in i2.RejAfterForm and
		s2 !in i.AppliedStudents and s2 !in i.RecommendedStudents
		one c: Company | no c.Offer
	#Company = 2
}
run show for 4
