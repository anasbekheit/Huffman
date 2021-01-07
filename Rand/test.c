int seqWrapper(int n,int m, int x, int y, int z)
{
if(n==0)
	return x;
if(n==1)
	return y;
if(n==2)
	return z;
	m = x + y;
	x = y;
	y = z;
        z = m;
	n--;
	return seqWrapper(n,m,x,y,z);
}

int seq(int n){
return seqWrapper(n,0,3 , 0, 2);
}

int seqj(int n)
{
	int x=3,y=0,z=2,m;
	if(n==0)
	return x;
	if (n==1)
	return y;
	if (n==2)
	return z;
	if(n>3)
	{
		m=x+y;
		x=y;
		y=z;
		z=m;
		return m;
	}
	
	return seqj(n-1);
}



int seqWrapper2(int n,int m, int x, int y, int z)
{
if(n==0)
	return x;
if(n==1)
	return y;
if(n==2)
	return z;
if(n>2){
	m = x + y;
	x=y;
	y=z;
	n--;
	return seqWrapper2(n,m,x,y,z);
}
return m;
}

int seq2(int n){
return seqWrapper2(n,0,3 , 0, 2);
}


int seqi(int n){
int  x =3,y=0,z=2;
int m;
if(n==0)
	return x;
if(n==1)
	return y;
if(n==2)
	return z;

while(n>2){
m = x + y;
x=y;
y=z;
z=m;
n--;
}
return m;
}

int main()
{
for(int i=0;i<50;i++){
printf("%d %d %d\n",seq2(i) ,seq(i), seqi(i));}

return 0;
}