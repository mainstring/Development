
public class piece 
{
	int color;	//0 for white, 1 for black, -1 for empty
	boolean kingStatus;
	
	public piece(int tempColor)
	{
		color=tempColor;
		kingStatus=false;
	}
	
	public piece()
	{
		color=-1;
		kingStatus=false;
	}
	
	void makeKing()
	{
		kingStatus=true;
	}
	
	

}
