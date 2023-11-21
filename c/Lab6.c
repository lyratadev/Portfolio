//Title: Lab6 write a problem that solves the producer - consumer problem. Produce and consume the alphabet.

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>

#define SIZEOFALPHABET 26
pthread_t producerT; 
pthread_t consumerT;
int buffer[1];
char alphabet[SIZEOFALPHABET];

sem_t mutex;
sem_t empty;
sem_t full;

//function prototypes + variables
int fetch();
void put(int);
void *producer(void *arg);
void *consumer(void *arg);
static int r = 0;
static int sentinel = 0;
static int start = 0;
static int done = 0;

//end psudocode

/*
char buffer[1];
char alphabet[26] = 
{ 'A', 'B', 'C', 'D', 
  'E', 'F', 'G', 'H', 
  'I', 'J', 'K', 'L', 
  'M', 'N', 'O', 'P', 
  'Q', 'R', 'S', 'T', 
  'U', 'V', 'W', 'X', 
  'Y', 'Z' } ;

char emptyArray[26];

*/

int main()
{
	sem_init(&empty, 0, 1); //init semaphores
	sem_init(&full, 0, 0);
	sem_init(&mutex,0,1);

	//start threads
	pthread_create(&producerT, NULL, producer, NULL);
	pthread_create(&consumerT, NULL, consumer, NULL);

	//join threads
	pthread_join(producerT, NULL);
	pthread_join(consumerT, NULL);

	printf("\t\nThreads returned");
	printf("\n");
	for (int i; i < 26; i++)
	{
		printf("%C ", alphabet[i]);
	}


	//remove semaphores
	sem_destroy(&mutex);
	sem_destroy(&full);
	sem_destroy(&empty);
	return 0;
}


void put(int i)
{
	buffer[0]=i;
}


int fetch()
{
	int readBuffer = buffer[0];
	sentinel++;
	return readBuffer;
}

//producer
void *producer(void *arg) 
	{ 
		for (int i = 65; i < (65+26); i++) {
		sem_wait(&empty);
		sem_wait(&mutex);
		put(i);
		printf("\n Inserting %c into buffer", i);
		sem_post(&mutex);
		sem_post(&full);
		}
	}
 
 //consumer
 void *consumer(void *arg) 
	{
	while (!done) 
		{
		sem_wait(&full);
		sem_wait(&mutex);
		int tmp = fetch();
		printf("\n\t Reading %c from buffer", tmp);

		sem_post(&mutex);
		sem_post(&empty);
		// do something with tmp ...
		alphabet[start] = tmp;
		start++;
		if (sentinel == 26 )
			{
				done = 1;
			}
		}
	}

