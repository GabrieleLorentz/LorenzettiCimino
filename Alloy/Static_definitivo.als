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
//StartDate: one Date, 
//EndDate: one Date, 
}

sig InterviewForm{
Compile: one Form,
CompiledBy: set Student
}

sig Form{}

/*
sig Date {
	Day: one Int, 
    Month: one Int,   
    Year: one Int,
}
*/

//FATTI

//An interviewForm must be unique and associated to exactly one internship. 
fact UniqueInterviewForm {
    all iF: InterviewForm | one iF.~ToCompile
    all f: Form | one f.~Compile
}


//recommendation
fact Bivalent_application_1 {
    all s: Student | s.ApplyTo in s.~AppliedStudents
	all i: Internship | i.AppliedStudents in i.~ApplyTo
}


fact Bivalent_recommendation_1 {
    all s: Student | s.RecommendedTo in s.~RecommendedStudents
	all i: Internship | i.RecommendedStudents in i.~RecommendedTo
}

fact UniqueOwned {
    all i: Internship | one i.~Offer
}
fact OwnershipConsistency {
    all c: Company, i: Internship |
        i in c.Offer implies i.OwnedBy = c
}
//A student can not be both selected or rejected before and after the form compilation, and 
fact noSelnorRec{
	all s: Student |all i: Internship | 
		s !in (i.RecommendedStudents & i.AppliedStudents)  
		and s !in (i.AllowedStudents & i.RejBefForm) 
		and s !in (i.SelectedStudents & i.RejAfterForm)
		and s !in (i.SelectedStudents & i.RejBefForm)
		and s !in (i.RejBefForm & i.RejAfterForm)
}

fact not_jump{
	all s: Student |all i: Internship | 
		(s !in (i.RecommendedStudents + i.AppliedStudents)) implies s !in (i.AllowedStudents + i.RejBefForm)
}


fact Allow_Process{
	all s: Student |all i: Internship | 
		(s in i.RecommendedStudents or s in i.AppliedStudents)
		implies ((some i.AllowedStudents or some i.RejBefForm) and s in i.AllowedStudents or s in i.RejBefForm)
}

fact Sel_Process{
	all s: Student |all i: Internship | 
		s in i.AllowedStudents
		implies ((some i.SelectedStudents or some i.RejAfterForm) and s in i.SelectedStudents or s in i.RejAfterForm)
}
	
fact Obliged_To_compile{
	all s: Student |all i: Internship | 
		s in i.(SelectedStudents + RejAfterForm) implies s in i.ToCompile.CompiledBy
}


/*

fact validDate {
    all d: Date | 
        d.Day >= 1 and d.Day <= 31 and
        d.Month >= 1 and d.Month <= 12 and
        d.Year >= 2025
}
/*
fact NoStartSecondInternshipBeforeEndFirst{
	//se lo studente viene selezionato per due internship e le accetta 
	//non può accettare la seconda se inizia prima della fine della prima
	//e così via, potrebbe essere utile la chiusura transitiva
	all s : Student | all sI1,sI2 : Internship |
		s in sI1.SelectedStudents and s in sI2.SelectedStudents implies
		sI2.StartDate.Year > sI1.StartDate.Year or
            (sI2.StartDate.Year = sI1.StartDate.Year and sI2.StartDate.Month > sI1.StartDate.Month) or
            (sI2.StartDate.Year = sI1.StartDate.Year and sI2.StartDate.Month = sI1.StartDate.Month and sI2.StartDate.Day > sI1.StartDate.Day)
}*/

pred show {
	one s, s2: Student | one i, i2: Internship | s in i.RecommendedStudents and s in i.SelectedStudents and 
																s !in i2.AppliedStudents and s !in i2.RecommendedStudents and
																s2 in i2.AppliedStudents and s2 in i2.RejAfterForm and 
																s2 !in i.AppliedStudents and s2 !in i.RecommendedStudents
	one c: Company | no c.Offer
	#Company  = 2
}
run show for 4







