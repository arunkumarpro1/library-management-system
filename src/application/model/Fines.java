package application.model;

// Generated Mar 11, 2017 10:05:54 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Fines generated by hbm2java
 */
@Entity
@Table(name = "fines", catalog = "library")
@NamedNativeQueries({
		@NamedNativeQuery(name = "Fines.InsertNewFines", query = "Insert into fines Select a.Loan_id,0.25*datediff(now(),a.Due_date),0 from book_loans a left join fines b on a.Loan_id = b.Loan_id where b.Loan_id is null and a.Datein is null and datediff(now(),a.Due_date)>0"),
		@NamedNativeQuery(name = "Fines.UpdateOldFines", query = "Update fines a inner join book_loans b on a.Loan_id=b.Loan_id set fine_amt = CASE WHEN b.Datein is null THEN 0.25*datediff(now(),b.Due_date) ELSE 0.25*datediff(b.Datein,b.Due_date) END where paid=0;") })
public class Fines implements java.io.Serializable {

	private static final long serialVersionUID = 949356517543595431L;
	private int loanId;
	private BookLoans bookLoans;
	private Double fineAmt;
	private Long paid;

	public Fines() {
	}

	public Fines(BookLoans bookLoans) {
		this.bookLoans = bookLoans;
	}

	public Fines(BookLoans bookLoans, Double fineAmt, Long paid) {
		this.bookLoans = bookLoans;
		this.fineAmt = fineAmt;
		this.paid = paid;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "bookLoans"))
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "Loan_id", unique = true, nullable = false)
	public int getLoanId() {
		return this.loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public BookLoans getBookLoans() {
		return this.bookLoans;
	}

	public void setBookLoans(BookLoans bookLoans) {
		this.bookLoans = bookLoans;
	}

	@Column(name = "Fine_amt", precision = 10, scale = 0)
	public Double getFineAmt() {
		return this.fineAmt;
	}

	public void setFineAmt(Double fineAmt) {
		this.fineAmt = fineAmt;
	}

	@Column(name = "Paid", precision = 10, scale = 0)
	public Long getPaid() {
		return this.paid;
	}

	public void setPaid(Long paid) {
		this.paid = paid;
	}

}
